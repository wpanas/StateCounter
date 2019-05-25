package com.github.wpanas.statecounter.action

import com.github.wpanas.statecounter.action.Action.ActionType.COUNTING
import com.github.wpanas.statecounter.action.Action.ActionType.TIMING
import org.junit.Assert.assertEquals
import org.junit.Test

class ActionTypeConverterTest {

    @Test
    fun `should convert ActionType to Int`() {
        // when
        val counting = ActionTypeConverter.fromActionType(COUNTING)
        val timing = ActionTypeConverter.fromActionType(TIMING)

        // then
        assertEquals(0, counting)
        assertEquals(1, timing)
    }

    @Test
    fun `should convert Int to ActionType`() {
        // when
        val counting = ActionTypeConverter.fromInt(0)
        val timing = ActionTypeConverter.fromInt(1)

        // then
        assertEquals(COUNTING, counting)
        assertEquals(TIMING, timing)
    }

}