package com.uxapp.oktava.ui.main.models

data class StepModel(
    val stepCount: Int,
    val stepName: String,
    val todos: List<String>,
    val advices: List<String>
) {
    companion object {
        fun first(): StepModel {
            val stepCount = 1
            val stepName = "Рефрен"
            val todos = listOf(
                "Выучить одинаковые элементы: припев, рефрен, риф",
                "Записать все выученные элементы"
            )
            val advices = listOf(
                "Если есть неодинаковые, но похожие куски, нужно разобраться в чем они разные и выучить",
                "Если ты учишь песню, то все элементы стоит учить сразу с вокальной партией"
            )
            return StepModel(stepCount, stepName, todos, advices)
        }
        fun second(): StepModel {
            val stepCount = 2
            val stepName = "Фрагмент"
            val todos = listOf(
                "Выучить фрагмент, который идёт до рефрена",
                "Записать новый фрагмент + рефрен"
            )
            val advices = listOf(
                "Если произведение начинается с рефрена/припева, то найди рефрен/припев в средней части произведения и выбери фрагмент до него. Для начала и конца будет будет выделено отдельное время"
            )
            return StepModel(stepCount, stepName, todos, advices)
        }
        fun third(): StepModel {
            val stepCount = 3
            val stepName = "Ф + Р + Ф"
            val todos = listOf(
                "Выучить фрагмент, который идёт после рефрена",
                "Записать рефрен + новый фрагмент",
                "Вспомнить и повторить первую запись",
                "Записать фрагмент + рефрен + фрагмент"
            )
            val advices = listOf(
                "Выбирай фрагменты и рефрены из средней части песни. Когда ты будешь исполнять композицию целиком, будешь чувствовать себя увереннее."
            )
            return StepModel(stepCount, stepName, todos, advices)
        }
        fun forth(): StepModel {
            val stepCount = 4
            val stepName = "Финал"
            val todos = listOf(
                "Выучить финал до последней ноты",
                "Повторить фрагмент до финала или выучить, если в предыдущих шагах не учили",
                "Выучить связку фрагмент + финал",
                "Записать связку с финалом"
            )
            val advices = listOf(
                "Продумай звучание финала. Финал -  это важная и запоминающаяся часть в произведении, он должен звучать убедительно и органично"
            )
            return StepModel(stepCount, stepName, todos, advices)
        }
        fun fifth(): StepModel {
            val stepCount = 5
            val stepName = "Вступление"
            val todos = listOf(
                "Выучить вступление",
                "Повторить фрагмент после вступления или выучить, если в предыдущих шагах не учили",
                "Выучить связку вступление + фрагмент",
                "Записать связку с вступлением"
            )
            val advices = listOf(
                "Вступление является не мене важной частью - ты задаёшь весь тон своему исполнению. Выбери такой темп, в котором тебе будет комфортно исполнять всё произведение",
                "Хорошо выучи фрагмент после вступления, что не потерять настрой. Иначе, произведение развалится"
            )
            return StepModel(stepCount, stepName, todos, advices)
        }
        fun sixth(): StepModel {
            val stepCount = 6
            val stepName = "Сборка"
            val todos = listOf(
                "Доучить все фрагменты",
                "Проверить и повторить все связки",
                "Попробовать сыграть произведение от начала до конца",
                "Записать композицию от начала до конца"
            )
            val advices = listOf(
                "Медленно проработай все места, где ты можешь сбиться. Добейся “бесшовного” звучания",
                "Для первого проигрывания не бери быстрый темп. Твоя задача сейчас - охватить вниманием всю композицию",
                "Если ты уверены в себе, то попробуй сыграть один раз с тем звучанием и настроем, с каким хочешь!",
                "Не играй произведение много раз подряд"
            )
            return StepModel(stepCount, stepName, todos, advices)
        }
        fun seventh(): StepModel {
            val stepCount = 7
            val stepName = "Исполнение"
            val todos = listOf(
                "Повтори всё произведение",
                "Запиши своё исполнение"
            )
            val advices = listOf(
                "Не записывай себя сразу, сначала разогрейся, повтори трудные места",
                "Сначала поставь запись, затем настройся, представь произведение целиком, проиграй в уме вступление и только после этого начинай играть.",
                "Исполни произведение  перед друзьями или родственниками! Так ты постепенно избавишься от волнения. Такие мини-концерты станут отличной мотивацией для тебя."
            )
            return StepModel(stepCount, stepName, todos, advices)
        }

        fun get(id: Int): StepModel {
            return when(id){
                0, 1 -> first()
                2 -> second()
                3 -> third()
                4 -> forth()
                5 -> fifth()
                6 -> sixth()
                7 -> seventh()
                else -> throw IllegalArgumentException("There is no step with number $id!")
            }
        }
    }
}