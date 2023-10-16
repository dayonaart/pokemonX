package id.dayona.pokemonx.utils

import android.util.Base64
import com.google.gson.Gson
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

object Utils {

    @Throws(IOException::class)
    fun compress(str: String?): String? {
        if (str.isNullOrEmpty()) {
            return str
        }
        println("String length : " + str.length)
        val out = ByteArrayOutputStream()
        val gzip = GZIPOutputStream(out)
        gzip.write(str.toByteArray())
        gzip.close()
        //        println("Output String lenght : " + outStr.length)
//        println("Output : $outStr")
        return Base64.encodeToString(out.toByteArray(), Base64.DEFAULT)
    }

    @Throws(IOException::class)
    inline fun <reified T> decompress(str: String?): T? {
        if (str.isNullOrEmpty()) {
            return null
        }
//        println("Input String length : " + str.length)
        val gis = GZIPInputStream(ByteArrayInputStream(Base64.decode(str, Base64.DEFAULT)))
        val out = ByteArrayOutputStream()
        val buffer = ByteArray(256)
        var n: Int
        while (gis.read(buffer).also { n = it } >= 0) {
            out.write(buffer, 0, n)
        }
//        val outStr = Base64.encodeToString(out.toByteArray(), Base64.DEFAULT)
//        println("Output String lenght : " + outStr.length)
        return Gson().fromJson(String(out.toByteArray()), T::class.java)
    }
}