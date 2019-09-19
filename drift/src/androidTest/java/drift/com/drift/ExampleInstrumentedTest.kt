package drift.com.drift

import android.content.Context
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import drift.com.drift.helpers.TextHelper

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    @Throws(Exception::class)
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()

        assertEquals("drift.com.drift.test", appContext.packageName)
    }



    @Test
    fun testWrappingNoURL() {

        val sampleMessage = "Hi there, this is great"

        assertEquals(TextHelper.wrapTextForSending(sampleMessage), sampleMessage)

    }

    @Test
    fun testSingleURL(){

        val sampleMessage = "Hi there, this is great! http://www.8bytes.ie"

        assertEquals(TextHelper.wrapTextForSending(sampleMessage), "Hi there, this is great! <a href=\"http://www.8bytes.ie\">http://www.8bytes.ie</a>")

    }

    @Test
    fun testMultipleURL(){

        val sampleMessage = "Hi there, this is great! http://www.8bytes.ie"

        assertEquals(TextHelper.wrapTextForSending(sampleMessage), "Hi there, this is great! <a href=\"http://www.8bytes.ie\">http://www.8bytes.ie</a>")

    }

    @Test
    fun testSameURL(){

        val sampleMessage = "Hi there, this is great! http://www.8bytes.ie http://www.8bytes.ie"

        val result = "Hi there, this is great! <a href=\"http://www.8bytes.ie\">http://www.8bytes.ie</a> <a href=\"http://www.8bytes.ie\">http://www.8bytes.ie</a>"

        assertEquals(TextHelper.wrapTextForSending(sampleMessage), result)

    }

    @Test
    fun testDifferentURLTypes(){


        val sampleMessage = "Hi there, this is great! http://www.8bytes.ie www.8bytes.ie 8bytes.ie"

        assertEquals(TextHelper.wrapTextForSending(sampleMessage), "Hi there, this is great! <a href=\"http://www.8bytes.ie\">http://www.8bytes.ie</a> <a href=\"http://www.8bytes.ie\">www.8bytes.ie</a> <a href=\"http://8bytes.ie\">8bytes.ie</a>")

    }

    @Test
    fun testAddingScheme(){

        var sampleMessage = "Hi there, this is great! www.8bytes.ie/"
        assertEquals(TextHelper.wrapTextForSending(sampleMessage), "Hi there, this is great! <a href=\"http://www.8bytes.ie/\">www.8bytes.ie/</a>")

        sampleMessage = "Hi there, this is great! 8bytes.ie"
        assertEquals(TextHelper.wrapTextForSending(sampleMessage), "Hi there, this is great! <a href=\"http://8bytes.ie\">8bytes.ie</a>")

        sampleMessage = "Hi there, this is great! http://8bytes.ie"
        assertEquals(TextHelper.wrapTextForSending(sampleMessage), "Hi there, this is great! <a href=\"http://8bytes.ie\">http://8bytes.ie</a>")

        sampleMessage = "Hi there, this is great! https://8bytes.ie"
        assertEquals(TextHelper.wrapTextForSending(sampleMessage), "Hi there, this is great! <a href=\"https://8bytes.ie\">https://8bytes.ie</a>")

        sampleMessage = "Hi there, this is great! https://www.8bytes.ie"
        assertEquals(TextHelper.wrapTextForSending(sampleMessage), "Hi there, this is great! <a href=\"https://www.8bytes.ie\">https://www.8bytes.ie</a>")

        //Trip white spaces
        sampleMessage = "Hi there, this is great! https://www.8bytes.ie \n"
        assertEquals(TextHelper.wrapTextForSending(sampleMessage), "Hi there, this is great! <a href=\"https://www.8bytes.ie\">https://www.8bytes.ie</a>")

    }

    @Test
    fun testNewLines() {
        val sampleMessage = "Hi there, you can find the pricing here: https://www.8bytes.ie\nHope this helps!"
        assertEquals(TextHelper.wrapTextForSending(sampleMessage), "Hi there, you can find the pricing here: <a href=\"https://www.8bytes.ie\">https://www.8bytes.ie</a>\nHope this helps!")
    }


    @Test
    fun testForEmojiiInText() {

        var sampleMessage = "Hi there, this is great! https://www.8bytes.ie \\uDDD3️ http://www.rte.ie"
        assertEquals(TextHelper.wrapTextForSending(sampleMessage), "Hi there, this is great! <a href=\"https://www.8bytes.ie\">https://www.8bytes.ie</a> \\uDDD3️ <a href=\"http://www.rte.ie\">http://www.rte.ie</a>")

        sampleMessage = "Hi there, this is great! https://www.8bytes.ie 😈 http://www.rte.ie"
        assertEquals(TextHelper.wrapTextForSending(sampleMessage), "Hi there, this is great! <a href=\"https://www.8bytes.ie\">https://www.8bytes.ie</a> 😈 <a href=\"http://www.rte.ie\">http://www.rte.ie</a>")

    }


    @Test
    fun testForEmailInText(){

        var message = "eoin@8bytes.ie"

        assertEquals(TextHelper.wrapTextForSending(message), "<a href=\"mailto:eoin@8bytes.ie\">eoin@8bytes.ie</a>")

        message = "eoin@8bytes.ie,adam@8bytes.ie"

        assertEquals(TextHelper.wrapTextForSending(message), "<a href=\"mailto:eoin@8bytes.ie\">eoin@8bytes.ie</a>,<a href=\"mailto:adam@8bytes.ie\">adam@8bytes.ie</a>")


    }

    @Test
    fun testSingleURLHTTPS(){
        val sampleMessage = "Hi there, this is great! https://www.8bytes.ie"
        assertEquals(TextHelper.wrapTextForSending(sampleMessage), "Hi there, this is great! <a href=\"https://www.8bytes.ie\">https://www.8bytes.ie</a>")

    }

    @Test
    fun testEmptyMarkdown(){
        val sampleMessage = "[]()"
        assertEquals(TextHelper.wrapTextForSending(sampleMessage), "<a href=\"\"></a>")

    }

    @Test
    fun testSingleMarkdownURL(){
        val sampleMessage = "Hi there, this is [great](https://www.8bytes.ie)!"
        val result = "Hi there, this is <a href=\"https://www.8bytes.ie\">great</a>!"
        assertEquals(TextHelper.wrapTextForSending(sampleMessage), result)

    }

    @Test
    fun testSingleMarkdownURLWithNoScheme(){
        val sampleMessage = "Hi there, this is [great](8bytes.ie)!"
        val result = "Hi there, this is <a href=\"8bytes.ie\">great</a>!"
        assertEquals(TextHelper.wrapTextForSending(sampleMessage), result)
    }

    @Test
    fun testMultipleMarkdownURLDifferentSchemes(){
        var sampleMessage = "Hi there, this is [great](8bytes.ie)!"
        var result = "Hi there, this is <a href=\"8bytes.ie\">great</a>!"
        assertEquals(TextHelper.wrapTextForSending(sampleMessage), result)


        sampleMessage = "Hi there, this is [great](http://8bytes.ie)!"
        result = "Hi there, this is <a href=\"http://8bytes.ie\">great</a>!"
        assertEquals(TextHelper.wrapTextForSending(sampleMessage), result)

        sampleMessage = "Hi there, this is [great](http://www.8bytes.ie)!"
        result = "Hi there, this is <a href=\"http://www.8bytes.ie\">great</a>!"
        assertEquals(TextHelper.wrapTextForSending(sampleMessage), result)
    }

    @Test
    fun testMixingMarkdownAndURL(){

        var sampleMessage = "Hi there, this is [great](8bytes.ie)! Or this 8bytes.ie"
        var result = "Hi there, this is <a href=\"8bytes.ie\">great</a>! Or this <a href=\"http://8bytes.ie\">8bytes.ie</a>"
        assertEquals(TextHelper.wrapTextForSending(sampleMessage), result)

        sampleMessage = "Hi there, this is [great](hi)! Or this http://8bytes.ie"
        result = "Hi there, this is <a href=\"hi\">great</a>! Or this <a href=\"http://8bytes.ie\">http://8bytes.ie</a>"
        assertEquals(TextHelper.wrapTextForSending(sampleMessage), result)

        sampleMessage = "Hi there, this is [great](hi)! Or this https://8bytes.ie"
        result = "Hi there, this is <a href=\"hi\">great</a>! Or this <a href=\"https://8bytes.ie\">https://8bytes.ie</a>"
        assertEquals(TextHelper.wrapTextForSending(sampleMessage), result)

    }

    @Test
    fun testMarkdownBold() {

        var sampleMessage = "Your very **bold**"
        var result = "Your very <strong>bold</strong>"
        assertEquals(TextHelper.wrapTextForSending(sampleMessage), result)

        sampleMessage = "Your very __bold__"
        result = "Your very <strong>bold</strong>"
        assertEquals(TextHelper.wrapTextForSending(sampleMessage), result)

    }


    @Test
    fun testMarkdownItalic() {

        var sampleMessage = "Your very _drunk_"
        var result = "Your very <em>drunk</em>"
        assertEquals(TextHelper.wrapTextForSending(sampleMessage), result)

        sampleMessage = "Your very *bold*"
        result = "Your very <em>bold</em>"
        assertEquals(TextHelper.wrapTextForSending(sampleMessage), result)

    }

    @Test
    fun testMarkdownCode() {

        val sampleMessage = "`var a = bold`"
        val result = "<code>var a = bold</code>"
        assertEquals(TextHelper.wrapTextForSending(sampleMessage), result)

    }
}
