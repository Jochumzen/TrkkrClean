package com.trkkr.trkkrclean.utilities

import ch.poole.openinghoursparser.*
import java.util.*


//DAY_RULE_OPEN, DAY_RULE_CLOSED, DAY_RULE_OFF, DAY_RULE_NOT_APPLICABLE, DAY_RANGE_RULE_OPEN, DAY_RANGE_RULE_CLOSED, DAY_RANGE_RULE_OFF, DAY_RANGE_RULE_NOT_APPLICABLE, DATE_RULE_OPEN, DATE_RULE_CLOSED, DATE_RULE_OFF, DATE_RULE_NOT_APPLICABLE, RULE_FAILED, TWENTYFOUR_SEVEN

enum class RuleResult {
    RULE_FAILED, NOT_APPLICABLE, TWENTYFOUR_SEVEN, YEAR_RULE_OPEN, YEAR_RULE_CLOSED, YEAR_RULE_OFF, WEEKS_RULE_OPEN, WEEKS_RULE_CLOSED, WEEKS_RULE_OFF, DATES_RULE_OPEN, DATES_RULE_CLOSED, DATES_RULE_OFF, DAYS_RULE_OPEN, DAYS_RULE_CLOSED, DAYS_RULE_OFF, TIMES_RULE_OPEN, TIMES_RULE_CLOSED, TIMES_RULE_OFF
}

enum class RuleCase {
    NO_CASE, NOT_APPLICABLE, APPLY
}

//Does not handle day events such as sunrise, sunset -> returns null

fun List<Rule>.evaluate(current: Date): Boolean? {
    val calendar = Calendar.getInstance()
    calendar.time = current
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
    val dayOfWeekParser = if (dayOfWeek == 1) 6 else dayOfWeek - 2
    val currentTime = calendar.get(Calendar.AM_PM)*720 + calendar.get(Calendar.HOUR)*60 + calendar.get(Calendar.MINUTE)
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val week = calendar.get(Calendar.WEEK_OF_YEAR)
    val date = calendar.get(Calendar.DATE)

    val results = this.map { it.evaluate(dayOfWeekParser, currentTime, year, month, week, date)}

    if(results.any {it == RuleResult.RULE_FAILED})
        return null

    if(results.any {it == RuleResult.YEAR_RULE_OFF || it == RuleResult.YEAR_RULE_CLOSED})
        return false

    if(results.any {it == RuleResult.YEAR_RULE_OPEN})
        return true

    if(results.any {it == RuleResult.YEAR_RULE_OFF || it == RuleResult.YEAR_RULE_CLOSED})
        return false

    if(results.any {it == RuleResult.YEAR_RULE_OPEN})
        return true

    if(results.any {it == RuleResult.WEEKS_RULE_OFF || it == RuleResult.WEEKS_RULE_CLOSED})
        return false

    if(results.any {it == RuleResult.WEEKS_RULE_OPEN})
        return true

    if(results.any {it == RuleResult.DATES_RULE_OFF || it == RuleResult.DATES_RULE_CLOSED})
        return false

    if(results.any {it == RuleResult.DATES_RULE_OPEN})
        return true

    if(results.any {it == RuleResult.DAYS_RULE_OFF || it == RuleResult.DAYS_RULE_CLOSED || it == RuleResult.TIMES_RULE_OFF})
        return false

    if(results.any {it == RuleResult.DAYS_RULE_OPEN})
        return true

    if(results.any {it == RuleResult.TIMES_RULE_CLOSED})
        return false

    if(results.any {it == RuleResult.TIMES_RULE_OPEN})
        return true

    if(results.any {it == RuleResult.TWENTYFOUR_SEVEN})
        return true

    return false
}

fun Rule.evaluate(dayOfWeekParser: Int, currentTime: Int, year: Int, month: Int, week: Int, date: Int): RuleResult
{
    if(times != null) {
        if (times!!.map { it.startEvent != null || it.endEvent != null}.any {it})
            return RuleResult.RULE_FAILED
    }

    if(isTwentyfourseven)
        return RuleResult.TWENTYFOUR_SEVEN

    val yearsRule = if (years != null) {
        if (years!!.contains(year))
            RuleCase.APPLY
        else
            RuleCase.NOT_APPLICABLE
    } else
        RuleCase.NO_CASE

    val weeksRule = if (weeks != null) {
        if (weeks!!.contains(week))
            RuleCase.APPLY
        else
            RuleCase.NOT_APPLICABLE
    } else
        RuleCase.NO_CASE

    val datesRule = if (dates != null) {
        if (dates!!.contains(year, month, date))
            RuleCase.APPLY
        else
            RuleCase.NOT_APPLICABLE
    } else
        RuleCase.NO_CASE

    val daysRule = if (days != null) {
        if (days!!.contains(dayOfWeekParser))
            RuleCase.APPLY
        else
            RuleCase.NOT_APPLICABLE
    } else
        RuleCase.NO_CASE


    if (yearsRule == RuleCase.NOT_APPLICABLE || weeksRule == RuleCase.NOT_APPLICABLE || datesRule == RuleCase.NOT_APPLICABLE || daysRule == RuleCase.NOT_APPLICABLE)
        return RuleResult.NOT_APPLICABLE

    //All rules are now applicable or no_case

    val timesRule = if (times != null) {
        if (times!!.contains(currentTime))
            RuleCase.APPLY
        else
            RuleCase.NOT_APPLICABLE
    } else
        RuleCase.NO_CASE



    if (yearsRule != RuleCase.NO_CASE) {
        return if(timesRule == RuleCase.NOT_APPLICABLE) {
            if(modifier?.modifier?.name == "OFF")
                RuleResult.NOT_APPLICABLE     //(e.g. 2021 10:00-12:00 off at 13:00)
            else
                RuleResult.YEAR_RULE_CLOSED   //(e.g. 2021 10:00-12:00 at 13:00)
        } else
            if(modifier?.modifier?.name == "OFF")
                RuleResult.YEAR_RULE_OFF    //(e.g. 2021 10:00-12:00 off at 11:00 or 2021 off)
            else
                RuleResult.YEAR_RULE_OPEN   //(e.g. 2021 10:00-12:00 at 11:00)
    }

    if (weeksRule != RuleCase.NO_CASE) {
        return if(timesRule == RuleCase.NOT_APPLICABLE) {
            if(modifier?.modifier?.name == "OFF")
                RuleResult.NOT_APPLICABLE     //(e.g. 2021 10:00-12:00 off at 13:00)
            else
                RuleResult.WEEKS_RULE_CLOSED   //(e.g. 2021 10:00-12:00 at 13:00)
        } else
            if(modifier?.modifier?.name == "OFF")
                RuleResult.WEEKS_RULE_OFF    //(e.g. 2021 10:00-12:00 off at 11:00 or 2021 off)
            else
                RuleResult.WEEKS_RULE_OPEN   //(e.g. 2021 10:00-12:00 at 11:00)
    }

    if (datesRule != RuleCase.NO_CASE) {
        return if(timesRule == RuleCase.NOT_APPLICABLE) {
            if(modifier?.modifier?.name == "OFF")
                RuleResult.NOT_APPLICABLE     //(e.g. 2021 10:00-12:00 off at 13:00)
            else
                RuleResult.DATES_RULE_CLOSED   //(e.g. 2021 10:00-12:00 at 13:00)
        } else
            if(modifier?.modifier?.name == "OFF")
                RuleResult.DATES_RULE_OFF    //(e.g. 2021 10:00-12:00 off at 11:00 or 2021 off)
            else
                RuleResult.DATES_RULE_OPEN   //(e.g. 2021 10:00-12:00 at 11:00)
    }

    if (daysRule != RuleCase.NO_CASE) {
        return if(timesRule == RuleCase.NOT_APPLICABLE) {
            if(modifier?.modifier?.name == "OFF")
                RuleResult.NOT_APPLICABLE     //(e.g. 2021 10:00-12:00 off at 13:00)
            else
                RuleResult.DAYS_RULE_CLOSED   //(e.g. 2021 10:00-12:00 at 13:00)
        } else
            if(modifier?.modifier?.name == "OFF")
                RuleResult.DAYS_RULE_OFF    //(e.g. 2021 10:00-12:00 off at 11:00 or 2021 off)
            else
                RuleResult.DAYS_RULE_OPEN   //(e.g. 2021 10:00-12:00 at 11:00)
    }

    if (timesRule != RuleCase.NO_CASE) {
        return if(timesRule == RuleCase.NOT_APPLICABLE) {
            if(modifier?.modifier?.name == "OFF")
                RuleResult.NOT_APPLICABLE     //(e.g. 10:00-12:00 off at 13:00)
            else
                RuleResult.TIMES_RULE_CLOSED   //(e.g. 10:00-12:00 at 13:00)
        } else
            if(modifier?.modifier?.name == "OFF")
                RuleResult.TIMES_RULE_OFF    //(e.g. 10:00-12:00 off at 11:00 )
            else
                RuleResult.TIMES_RULE_OPEN   //(e.g. 10:00-12:00 at 11:00)
    }

    return RuleResult.YEAR_RULE_OPEN


}

@JvmName("containsWeekDayRange")
fun List<WeekDayRange>.contains(dayOfWeek: Int) : Boolean {

    return this.map { it.contains(dayOfWeek)}.any { it }

}

fun WeekDayRange.contains(dayOfWeek: Int) : Boolean {
    return if(endDay == null) {
        startDay.ordinal == dayOfWeek
    } else {
        (startDay.ordinal <= dayOfWeek && endDay.ordinal >= dayOfWeek)
    }
}

@JvmName("containsTimeSpan")
fun List<TimeSpan>.contains(time: Int) : Boolean {

    return this.map { it.contains(time)}.any { it }
}

fun TimeSpan.contains(time: Int) : Boolean {
    return (time in start until end)
}

@JvmName("containsDateRange")
fun List<DateRange>.contains(year: Int, month: Int, date: Int) : Boolean {

    return this.map { it.contains(year, month, date)}.any { it }

}

fun DateRange.contains(year: Int, month: Int, day: Int) : Boolean {

    return if(endDate == null) {

        val yearOK = (startDate.year < 0 || startDate.year == year)
        val monthOK = (startDate.month.ordinal < 0 || startDate.month.ordinal == month)
        val dateOK = (startDate.day < 0 || startDate.day == day)

        yearOK && monthOK && dateOK

    } else {

        val startMonthOrdinal =  startDate.month.ordinal
        val endMonthOrdinal = if (endDate!!.month == null) startMonthOrdinal else endDate!!.month.ordinal

        val yearOK = (startDate.year < 0 || endDate!!.year < 0 || year in startDate.year..endDate!!.year)
        val monthOK = (startDate.month.ordinal < 0 || endMonthOrdinal < 0 || month in startMonthOrdinal..endMonthOrdinal)
        val dateOK = (startDate.day < 0 || endDate!!.day < 0 || day in startDate.day..endDate!!.day)

        yearOK && monthOK && dateOK
    }
}

@JvmName("containsYearRange")
fun List<YearRange>.contains(year: Int) : Boolean {

    return this.map { it.contains(year)}.any { it }

}

fun YearRange.contains(year: Int) : Boolean {

    return if (endYear > 0)
            year in startYear..endYear
        else
            year == startYear
}

@JvmName("containsWeekRange")
fun List<WeekRange>.contains(week: Int) : Boolean {

    return this.map { it.contains(week)}.any { it }

}

fun WeekRange.contains(week: Int) : Boolean {

    return if (endWeek > 0)
        week in startWeek..endWeek
    else
        week == startWeek
}
