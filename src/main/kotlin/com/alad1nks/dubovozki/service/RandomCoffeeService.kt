package com.alad1nks.dubovozki.service

import com.alad1nks.dubovozki.model.*
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

private const val RANDOM_COFFEE_MATCH = "RANDOM_COFFEE_MATCH"
private const val RANDOM_COFFEE_SEARCHING_USER = "RANDOM_COFFEE_SEARCHING_USER"
private const val RANDOM_COFFEE_UNUSED_WORD = "RANDOM_COFFEE_UNUSED_WORD"

@Service
class RandomCoffeeService(
    private val stringRedisTemplate: RedisTemplate<String, String>,
    private val emailAndTelegramUsernameRedisTemplate: RedisTemplate<String, EmailAndTelegramUsername>,
    private val randomCoffeeMatchRedisTemplate: RedisTemplate<String, RandomCoffeeMatch>
) {

    fun addWords(words: List<String>): List<String> {
        val setOperations = stringRedisTemplate.opsForSet()
        words.forEach { word ->
            setOperations.add(RANDOM_COFFEE_UNUSED_WORD, word)
        }

        return setOperations.members(RANDOM_COFFEE_UNUSED_WORD)?.toList() ?: emptyList()
    }

    @Scheduled(cron = "0 0 11 * * FRI")
    fun generatePairs(): RandomCoffeeStatus {
        val stringSetOperations = stringRedisTemplate.opsForSet()
        val emailAndTelegramUsernameSetOperations = emailAndTelegramUsernameRedisTemplate.opsForSet()
        val matchSetOperations = randomCoffeeMatchRedisTemplate.opsForSet()

        val unusedWords = getUnusedWords()
        val searchingUsers = getSearchingUsers().shuffled()

        for (i in 0 until minOf(searchingUsers.size / 2, unusedWords.size)) {
            val word = unusedWords[i]
            val firstUser = searchingUsers[2 * i]
            val secondUser = searchingUsers[2 * i + 1]
            val match = RandomCoffeeMatch(word, firstUser, secondUser)

            stringSetOperations.members(RANDOM_COFFEE_UNUSED_WORD)?.remove(word)
            emailAndTelegramUsernameSetOperations.members(RANDOM_COFFEE_SEARCHING_USER)?.remove(firstUser)
            emailAndTelegramUsernameSetOperations.members(RANDOM_COFFEE_SEARCHING_USER)?.remove(secondUser)
            matchSetOperations.add(RANDOM_COFFEE_MATCH, match)
        }

        val status = getStatus()

        return status
    }

    fun join(telegramUsername: TelegramUsername): EmailAndTelegramUsername {
        val setOperations = emailAndTelegramUsernameRedisTemplate.opsForSet()
        val email = SecurityContextHolder.getContext().authentication.name
        val emailAndTelegramUsername = EmailAndTelegramUsername(email, telegramUsername.username)

        setOperations.add(RANDOM_COFFEE_SEARCHING_USER, emailAndTelegramUsername)

        return emailAndTelegramUsername
    }

    fun getMyStatus(): RandomCoffeeMyStatus {
        val email = SecurityContextHolder.getContext().authentication.name
        val match = getMatch(email)

        match?.let { return it }

        val isSearching = isSearching(email)

        return RandomCoffeeMyStatus.Searching(isSearching)
    }

    fun getStatus(): RandomCoffeeStatus {
        val unusedWords = getUnusedWords()
        val searchingUsers = getSearchingUsers()
        val matches = getMatches()

        return RandomCoffeeStatus(unusedWords, searchingUsers, matches)
    }

    private fun isSearching(email: String): Boolean {
        val searchingUsers = getSearchingUsers()

        searchingUsers.forEach { searchingUser ->
            if (searchingUser.email == email) {
                return true
            }
        }

        return false
    }

    private fun getMatch(email: String): RandomCoffeeMyStatus.Match? {
        val matches = getMatches()

        matches.forEach { (word, user1, user2) ->
            if (user1.email == email) {
                return RandomCoffeeMyStatus.Match(word, user2.email)
            }

            if (user2.email == email) {
                return RandomCoffeeMyStatus.Match(word, user1.email)
            }
        }

        return null
    }

    private fun getUnusedWords(): List<String> {
        val setOperations = stringRedisTemplate.opsForSet()
        val unusedWords = setOperations.members(RANDOM_COFFEE_UNUSED_WORD)?.toList() ?: emptyList()

        return unusedWords
    }

    private fun getSearchingUsers(): List<EmailAndTelegramUsername> {
        val setOperations = emailAndTelegramUsernameRedisTemplate.opsForSet()
        val searchingUsers = setOperations.members(RANDOM_COFFEE_SEARCHING_USER)?.toList() ?: emptyList()

        return searchingUsers
    }

    private fun getMatches(): List<RandomCoffeeMatch> {
        val setOperations = randomCoffeeMatchRedisTemplate.opsForSet()
        val matches = setOperations.members(RANDOM_COFFEE_MATCH)?.toList() ?: emptyList()

        return matches
    }
}
