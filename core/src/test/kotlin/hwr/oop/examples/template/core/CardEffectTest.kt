package hwr.oop.examples.template.core

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CardEffectTest {

    private fun ctx(): GameContext {
        val player = ActivePlayer.create(
            Player(PlayerId("p1"), PlayerCards(hand = listOf(Card.COPPER)))
        )

        val state = BoardState(
            market = GameMarket(emptySet()),
            players = listOf(player.player)
        )

        return GameContext(player, state)
    }

    @Test
    fun `done effect produces action phase game`() {
        val effect = CardEffect.done(null, ctx())

        val result = effect.execute()

        assertEquals(GamePhase.ActionPhase, result.activePlayer.let { GamePhase.ActionPhase })
    }

    @Test
    fun `pending effect produces effect-active game`() {
        val effect = CardEffect.pending(
            null,
            ctx(),
            listOf(
                PendingChoice(PlayerId("p1"), "type", "desc", listOf("a"), 1, 1)
            )
        )

        val result = effect.execute()

        // key assertion: pending must not behave like done
        val done = CardEffect.done(null, ctx()).execute()

        assertNotEquals(done.activePlayer, result.activePlayer)
    }

    @Test
    fun `single step completes execution`() {
        val ctx = ctx()

        val step: EffectStep = { _, _ ->
            CardEffect.done(null, ctx)
        }

        val effect = CardEffect.stepwise(null, ctx, listOf(step))

        val result = effect.execute()

        val baseline = CardEffect.done(null, ctx).execute()

        assertEquals(baseline.activePlayer.id(), result.activePlayer.id())
    }

    @Test
    fun `multiple steps still complete successfully`() {
        val ctx = ctx()

        val step1: EffectStep = { _, _ ->
            CardEffect.stepwise(null, ctx, emptyList())
        }

        val step2: EffectStep = { _, _ ->
            CardEffect.done(null, ctx)
        }

        val effect = CardEffect.stepwise(null, ctx, listOf(step1, step2))

        val result = effect.execute()

        val expected = CardEffect.done(null, ctx).execute()

        assertEquals(expected.activePlayer.id(), result.activePlayer.id())
    }

    @Test
    fun `answer modifies internal state before execution`() {
        val ctx = ctx()

        val step: EffectStep = { _, answers ->
            assertTrue(answers.any())
            CardEffect.done(null, ctx)
        }

        val effect = CardEffect.stepwise(null, ctx, listOf(step))

        val result = effect.answer(
            ctx,
            AnsweredChoice("p1", listOf("choice"))
        )

        val baseline = CardEffect.done(null, ctx).execute()

        assertEquals(baseline.activePlayer.id(), result.activePlayer.id())
    }

    @Test
    fun `no steps behaves like immediate completion`() {
        val effect = CardEffect.stepwise(null, ctx(), emptyList())

        val result = effect.execute()

        val baseline = CardEffect.done(null, ctx()).execute()

        assertEquals(baseline.activePlayer.id(), result.activePlayer.id())
    }

    @Test
    fun `card effect exposes internal fields`() {
        val ctx = ctx()

        val effect = CardEffect.stepwise(
            Card.COPPER,
            ctx,
            emptyList()
        )

        assertEquals(Card.COPPER, effect.card)
        assertTrue(effect.pending.isEmpty())
        assertTrue(effect.answers.isEmpty())
    }
}