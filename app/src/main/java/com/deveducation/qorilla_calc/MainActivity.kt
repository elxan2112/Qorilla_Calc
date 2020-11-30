package com.deveducation.qorilla_calc

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder
import org.w3c.dom.Text
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var math_op: TextView //Переменная для работы с вводимыми элементами
    var math_op_temp: String = "" //Переменная для хранения вводимыъ элементов
    private lateinit var res_text: TextView //Переменная для работы с результатом вычислений

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        math_op = findViewById(R.id.math_operation)
        res_text = findViewById(R.id.result_text)
    }

    fun onNumberClick(view: View) { //Функция для работы цифр
        var display = math_op.text.toString()
        if (display == "0")
            math_op.text = view.tag.toString()
        else if(display == "-0")
            math_op.text = "-" + view.tag.toString()
        else
            math_op.text = display + view.tag.toString()
    }

    fun onOperationClick(view: View) { //Функция для работы со знаками

        var op_disp = math_op.text.toString()

        if(view.tag.toString() == "11"){ //Плюс
            if (math_op.text == ""){}
            else if(math_op.text == "-" || math_op.text == "*" || math_op.text == "+" || math_op.text == "/" ){
                if (math_op_temp == ""){}
                else
                math_op.text = "+"
            }
            else{
                math_op_temp = math_op_temp + op_disp
                math_op.text ="+"
            }
        }
        else if(view.tag.toString() == "12"){ //Минус
            if (math_op.text == "" || math_op.text == "-"){
                math_op.text ="-"
            }
            else if (math_op.text == "+" || math_op.text == "*" || math_op.text == "-" || math_op.text == "/"){
                math_op.text = op_disp + "-"
            }
            else if(math_op.text == "+-")
                math_op.text = "+-"
            else if(math_op.text == "*-")
                math_op.text = "*-"
            else if (math_op.text == "/-")
                math_op.text = "/-"
            else{
                math_op_temp = math_op_temp + op_disp
                math_op.text ="-"
            }
        }
        else if(view.tag.toString() == "13"){ //Умножение
            if (math_op.text == ""){}
            else if(math_op.text == "-" || math_op.text == "+" || math_op.text == "*" || math_op.text == "/"){
                if (math_op_temp == ""){}
                else
                math_op.text = "*"
            }
            else{
                math_op_temp = math_op_temp + op_disp
                math_op.text ="*"
            }
        }
        else if(view.tag.toString() == "14"){ //Деление
            if (math_op.text == ""){}
            else if(math_op.text == "-" || math_op.text == "*" || math_op.text == "/" || math_op.text == "+"){
                if (math_op_temp == ""){}
                else
                math_op.text = "/"
            }
            else{
                math_op_temp = math_op_temp + op_disp
                math_op.text ="/"
            }
        }

        else if (view.tag.toString() == "15") { //Полное очищение
            math_op.text = ""
            res_text.text = ""
            math_op_temp = ""
        } else if (view.tag.toString() == "16") { //Удаление последнего элемента
            val str = math_op.text.toString()
            if (str.isNotEmpty()) {
                math_op.text = str.substring(0, str.length - 1)
                res_text.text = ""
            }
        } else if (view.tag.toString() == "17") { //Точка
            if (math_op.text.contains(".")) {
            } else if(math_op.text == "" || math_op.text == "+" || math_op.text == "-" || math_op.text == "*" || math_op.text == "/")
                math_op.text = op_disp + "0."
            else
                math_op.text = math_op.text.toString() + "."
        } else if (view.tag.toString() == "10") { //Вычисление результата (равно)

            math_op_temp = math_op_temp + op_disp
            if (math_op.text == "" || math_op.text == "+" || math_op.text == "-" || math_op.text == "*" || math_op.text == "/"){
                res_text.text = "Ошибка! Будьте аккуратнее!"
                math_op.text = ""
                math_op_temp = ""
            }
            else{
                try {
                val ops = ExpressionBuilder(math_op_temp).build()
                val result = ops.evaluate()
                val longResult = result.toLong()
                if (result == longResult.toDouble()) {
                    //var res = "%.6f".format(longResult.toString())
                    //res_text.text = (("%.2f".format(longResult)).toDouble()).toString()

                    res_text.text = longResult.toString()
                    math_op.text = ""
                    math_op_temp = ""
                } else {
                    res_text.text = result.toString()
                    math_op.text = ""
                    math_op_temp = ""
                }
                    }
                catch (exp: Exception) {
                Log.d("Error!", "${exp.message}")
                res_text.text = "${exp.message}"
                    math_op.text = ""
                    math_op_temp = ""
                }
            }
        }
    }
}
