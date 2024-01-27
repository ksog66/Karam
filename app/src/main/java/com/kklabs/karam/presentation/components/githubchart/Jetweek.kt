import android.os.Build
import androidx.annotation.RequiresApi
import com.kklabs.karam.presentation.components.githubchart.JetCalendarType
import com.kklabs.karam.presentation.components.githubchart.JetDay
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)

fun dayNames(dayOfWeek: DayOfWeek): List<String> {
    val days = mutableListOf<DayOfWeek>()
    days.add(dayOfWeek)
    while (days.size != 7) {
        days.add(days.last().plus(1))
    }
    return days.map {
        it.getDisplayName(
            TextStyle.NARROW,
            Locale.getDefault()
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)

class JetWeek private constructor(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val monthOfWeek: Int,
    val firstDayOfWeek: DayOfWeek,
) : JetCalendarType() {
    lateinit var days: List<JetDay>

    companion object {
        fun current(
            date: LocalDate = LocalDate.now(),
            dayOfWeek: DayOfWeek
        ): JetWeek {
            val startOfCurrentWeek: LocalDate =
                date.with(TemporalAdjusters.previousOrSame(dayOfWeek))
            val lastDayOfWeek = dayOfWeek.plus(6) // or minus(1)
            val endOfWeek: LocalDate = date.with(TemporalAdjusters.nextOrSame(lastDayOfWeek))
            val week = JetWeek(startOfCurrentWeek, endOfWeek, date.monthValue, dayOfWeek)
            week.days = week.dates()
            return week
        }
    }

    fun dates(): List<JetDay> {
        val days = mutableListOf<JetDay>()
        val isPart = startDate.monthValue == this.monthOfWeek
        days.add(startDate.toJetDay(isPart))
        while (days.size != 7) {
            days.add(days.last().nextDay(this))
        }
        return days
    }

}


fun LocalDate.toJetDay(isPart: Boolean): JetDay {
    return JetDay(this, isPart)
}

@RequiresApi(Build.VERSION_CODES.O)

private fun JetDay.nextDay(jetWeek: JetWeek): JetDay {
    val date = this.date.plusDays(1)
    val isPartOfMonth = this.date.plusDays(1).monthValue == jetWeek.monthOfWeek
    return JetDay(date, isPartOfMonth)
}

@RequiresApi(Build.VERSION_CODES.O)

fun JetWeek.nextWeek(): JetWeek {
    val firstDay = this.endDate.plusDays(1)
    val week = JetWeek.current(firstDay, dayOfWeek = firstDayOfWeek)
    week.days = week.dates()
    return week
}

enum class JetViewType {
    MONTHLY,
    WEEKLY,
    YEARLY;

    fun next(): JetViewType {
        if (ordinal == values().size.minus(1)) {
            return MONTHLY
        }
        return values()[ordinal + 1]
    }
}