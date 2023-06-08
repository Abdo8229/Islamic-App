package com.example.islamapplictation.data.quranprovider

import android.annotation.SuppressLint
import android.content.Context
import java.text.DecimalFormat

class PagesManger {




        @SuppressLint("SuspiciousIndentation", "DiscouragedApi")
        fun getQuranImagesPageByNumber(context: Context, pageNumber: Int): Int {
            val formatter = DecimalFormat("000")
            val drawableName = "page${formatter.format(pageNumber)}"
                    return  context.resources.getIdentifier(drawableName,"drawable",context.packageName)
    }
}