package com.example.focusstarthomework.kotlin.koans

data class Course(val courseName: String, val lessons: List<Lesson>)
data class Lesson(val title: String, val description: String)

fun getLessons(course: Course) = course.lessons.toMutableList()

fun getLessonsTitles(coursesList: List<Course>) {
    coursesList.map { it.lessons.map { it.title } }
}

fun getCourse(coursesList: List<Course>, courseName: String) {
    coursesList.filter { it.courseName == courseName }
}

fun predicates(lessonsList: List<Lesson>, coursesList: List<Course>) {
    coursesList.find { it.lessons == lessonsList }
    coursesList.any { it.lessons.isEmpty() }
    coursesList.all { it.courseName.isNotEmpty() }
}

fun flatMap(coursesList: List<Course>) {
    coursesList.flatMap { it.courseName.toList() }
}

fun maxMin(coursesList: List<Course>) {
    coursesList.maxBy { it.lessons.size }
    coursesList.minBy { it.courseName.length < 10 }
}

fun sort(coursesList: List<Course>) {
    coursesList.sortedBy { it.courseName.length }
}

fun sum(coursesList: List<Course>) {
    coursesList.sumBy { it.lessons.size }
}

fun groupBy(coursesList: List<Course>) {
    coursesList.groupBy { it.lessons.size }
}

fun partition(coursesList: List<Course>) {
    val (positive, negative) = coursesList
        .flatMap { it.lessons }
        .partition { it.description.isNotEmpty() }
}

fun fold(coursesList: List<Course>) {

}

fun compoundTask(coursesList: List<Course>) {
    val t = coursesList.map { it.lessons }.filter { it.isEmpty() }.flatten()
}