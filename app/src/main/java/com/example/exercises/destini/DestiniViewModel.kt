package com.example.exercises.destini

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DestiniViewModel : ViewModel() {
    private val _index = MutableStateFlow(0)
    val index: StateFlow<Int> = _index

    val scenarios = listOf(
        Scenario(
            "Your car has blown a tire on a winding road in the middle of nowhere with no cell phone reception. You decide to hitchhike. A rusty pickup truck rumbles to a stop next to you. A man with a wide brimmed hat with soulless eyes opens the passenger door for you and asks: Need a ride, boy?",
            "I\'ll hop in. Thanks for the help!",
            "Better ask him if he\'s a murderer first."
        ),
        Scenario(
            "He nods slowly, unphased by the question.",
            "At least he\'s honest. I\'ll climb in.",
            "Wait, I know how to change a tire."
        ),
        Scenario(
            "As you begin to drive, the stranger starts talking about his relationship with his mother. He gets angrier and angrier by the minute. He asks you to open the glovebox. Inside you find a bloody knife, two severed fingers, and a cassette tape of Elton John. He reaches for the glove box.",
            "I love Elton John! Hand him the cassette tape.",
            "It\'s him or me! You take the knife and stab him."
        ),
        Scenario(
            "What? Such a cop out! Did you know traffic accidents are the second leading cause of accidental death for most adult age groups?",
            "Restart",
            ""
        ),
        Scenario(
            "As you smash through the guardrail and careen towards the jagged rocks below you reflect on the dubious wisdom of stabbing someone while they are driving a car you are in.",
            "Restart",
            ""
        ),
        Scenario(
            "You bond with the murderer while crooning verses of Can you feel the love tonight. He drops you off at the next town. Before you go he asks you if you know any good places to dump bodies. You reply: \"Try the pier\" ",
            "Restart",
            ""
        )
    )

    fun nextStory(choice : Int) {
        if (_index.value > 3) _index.value = 0;
        else if (choice == 1) {
            if (_index.value == 0) _index.value += 2;
            else if (_index.value == 1) _index.value++;
            else _index.value += 3;
        } else if (choice == 2) {
            if (_index.value == 0) _index.value++;
            else _index.value += 2;
        }
    }
}

data class Scenario(
    val title: String,
    val choice1: String,
    val choice2: String,
)