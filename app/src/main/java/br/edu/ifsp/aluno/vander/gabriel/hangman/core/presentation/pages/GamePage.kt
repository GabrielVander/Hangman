package br.edu.ifsp.aluno.vander.gabriel.hangman.core.presentation.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Game
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Round
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Word
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.presentation.manager.MainViewModel

@Composable
fun GamePage(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val game: Game? by mainViewModel.currentGame.observeAsState(null)

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (game?.currentRound == null) {
                Start(
                    onStart = { mainViewModel.startNewRound() }
                )
            } else {
                WordDisplay(game!!.currentRound!!)
            }
        }
    }
}

@Composable
private fun WordDisplay(round: Round) {
    val word: Word = round.word
    val chosenLetters: List<Char> = round.guessedLetters

    Text(
        modifier = Modifier,
        fontSize = 5.em,
        text = buildObfuscatedWord(
            word = word.value,
            chosenLetters = chosenLetters
        )
    )
}

@Composable
private fun Start(
    onStart: () -> Unit = {}
) {
    Text(text = "Let's go!", fontSize = 42.sp)
    Spacer(modifier = Modifier.height(15.dp))
    Button(onClick = onStart) {
        Text(text = "Start")
    }
}

fun buildObfuscatedWord(word: String, chosenLetters: List<Char>): String {
    return buildString {
        for (character in word) {
            if (word.indexOf(character) > 0) {
                append(' ')
            }
            append(if (chosenLetters.contains(character)) character else '_')
        }
    }
}