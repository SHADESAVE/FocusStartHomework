package com.example.focusstarthomework.kotlin.koans

import com.example.focusstarthomework.R
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PropertyExample() {
    var counter = 0
    var propertyWithCounter: Int? = null
        set(v: Int?) {
            field = v
            counter++
        }
}

fun lazyProperty() {
    val lazyText by lazy{
        println("lazy prop initialized!")
        "Wooah"
    }
}

fun delegate() {
    var person: Person by PersonDelegate()
    person = Person("vASDASD")
    print(person) // Person(name=Vasdasd, age=null)
}

class PersonDelegate : ReadWriteProperty<Nothing?, Person> {

    private var person: Person? = null

    override fun getValue(thisRef: Nothing?, property: KProperty<*>): Person {
        return person!!
    }

    override fun setValue(thisRef: Nothing?, property: KProperty<*>, value: Person) {
        person = value.copy(
            name = (value.name.first().toUpperCase() + value.name.substring(1).toLowerCase())
        )
    }
}
