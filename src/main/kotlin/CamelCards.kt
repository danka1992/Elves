import java.io.File

object CamelCards {

    fun orderCards() {
        val fileName = "src/main/resources/CamelCards.txt"
        val file = File(fileName).readLines()

        val cardsMap = mutableMapOf<Hands, MutableList<Pair<String, Int>>>()
        for(handType in Hands.entries) cardsMap[handType] = mutableListOf()

        val order = "AKQT98765432J"
        val orderMap = order.withIndex().associate { it.value to it.index }

        val comparator = Comparator<Pair<String, Any>> { p1, p2 ->
            for (i in p1.first.indices) {
                val charOrder1 = orderMap[p1.first[i]] ?: Int.MAX_VALUE
                val charOrder2 = orderMap[p2.first[i]] ?: Int.MAX_VALUE
                if (charOrder1 != charOrder2) {
                    return@Comparator charOrder1 - charOrder2
                }
            }
            0
        }

        file.forEach {
            val cardsOnHand = it.split(" ")
            val typeOfHand = identifyKeyOfHandWithJokers(cardsOnHand[0])
            cardsMap[typeOfHand]!!.add(Pair(cardsOnHand[0], cardsOnHand[1].toInt()))
        }

        var entries = cardsMap.values.sumOf { it.size }
        var result = 0

        for(handType in Hands.entries) {
            cardsMap[handType]?.sortWith(comparator)
            val size = cardsMap[handType]?.size
            for (hand in 0..<size!!) {
                println("Carta ${cardsMap[handType]!![hand].first}, hand type $handType, hodnota ${cardsMap[handType]!![hand].second}, nasobok $entries")
                result += cardsMap[handType]!![hand].second * entries
                entries --
            }
        }
        println("Result: $result")
    }

    private fun identifyKeyOfHand(hand: String): Hands {
        //val hand = turn.split(" ")

        val countOfFirstCard = hand.count { it == hand[0] }
        if (countOfFirstCard == 5) return Hands.KIND_5
        if (countOfFirstCard == 4) return Hands.KIND_4

        val countOfSecondCard = hand.count { it == hand[1] }
        if (countOfSecondCard == 4) return Hands.KIND_4

        val countOfThirdCard = hand.count { it == hand[2] }
        val countOfFourthCard = hand.count { it == hand[3] }
        val countOfFifthCard = hand.count {it == hand[4]}

        val countOfDuplicates = listOf(countOfFirstCard, countOfSecondCard, countOfThirdCard, countOfFourthCard, countOfFifthCard).count { it == 2 }
        if (countOfThirdCard == 3 || countOfFirstCard == 3 || countOfSecondCard == 3) {
            return if (countOfDuplicates == 2) Hands.FULL_HOUSE
            else Hands.KIND_3
        }

        if(countOfDuplicates == 4) return Hands.PAIRS_2 else if (countOfDuplicates == 2) return Hands.PAIR
        return Hands.HIGH_CARD
    }

    private fun identifyKeyOfHandWithJokers(hand: String): Hands {

        val countOfJokers = hand.count { it == 'J' }
        val countOfFirstCard = hand.count { it == hand[0] && it != 'J'}
        if (countOfFirstCard + countOfJokers == 5) return Hands.KIND_5

        val countOfSecondCard = hand.count { it == hand[1]  && it != 'J'}
        if (countOfSecondCard + countOfJokers == 5) return Hands.KIND_5

        val countOfThirdCard = hand.count { it == hand[2]  && it != 'J'}
        if (countOfSecondCard + countOfJokers == 5) return Hands.KIND_5

        val countOfFourthCard = hand.count { it == hand[3]  && it != 'J'}
        if (countOfFourthCard + countOfJokers == 5) return Hands.KIND_5


        val countOfFifthCard = hand.count {it == hand[4]  && it != 'J'}
        if (countOfFifthCard + countOfJokers == 5) return Hands.KIND_5


        if (countOfFirstCard + countOfJokers == 4 ) return Hands.KIND_4
        if (countOfSecondCard + countOfJokers == 4) return Hands.KIND_4
        if (countOfThirdCard + countOfJokers == 4) return Hands.KIND_4
        if (countOfFourthCard + countOfJokers == 4) return Hands.KIND_4
        if (countOfFifthCard + countOfJokers == 4) return Hands.KIND_4

        val countsList = listOf(countOfFirstCard, countOfSecondCard, countOfThirdCard, countOfFourthCard, countOfFifthCard)

        val countOfTriplicates = countsList.count { it == 3 }
        val countOfDuplicates = countsList.count { it == 2 }
        val countOfSingleCards = countsList.count { it == 1 }

        if ((countOfDuplicates == 4 && countOfJokers == 1) || (countOfDuplicates == 2 && countOfTriplicates == 3)) return Hands.FULL_HOUSE
        if ((countOfDuplicates == 2 && countOfJokers == 1) || (countOfTriplicates == 3) || (countOfSingleCards == 3 && countOfJokers == 2)) return Hands.KIND_3

        if (countOfDuplicates == 4) return Hands.PAIRS_2
        if (countOfDuplicates == 2) return Hands.PAIR


        if(countOfSingleCards  == 4 && countOfJokers == 1) return Hands.PAIR

        return Hands.HIGH_CARD
    }


    enum class Hands {
        KIND_5,
        KIND_4,
        FULL_HOUSE,
        KIND_3,
        PAIRS_2,
        PAIR,
        HIGH_CARD
    }
}

