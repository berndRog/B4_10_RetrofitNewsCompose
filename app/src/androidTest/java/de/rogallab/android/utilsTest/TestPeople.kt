package de.rogallab.android.utilsTest

class TestPeople {

   val person1 = Person(
      id = 1,
      name = "Bernd Rogalla",
      email = "b-u.rogalla@ostfalia.de",
      phone = "05826 988 61180",
      imagePath = "file:/data/data/de.rogallab.android/app_Images/man_1.jpg"
   )
   val person2 = Person(
      id = 2,
      name = "Albrecht Meissner",
      email = "al.meissner@ostfalia.de",
      phone = "05826 988 61160",
      imagePath = "file:/data/data/de.rogallab.android/app_Images/man_2.png"
   )
   val person3 = Person(
      id = 3,
      name = "Arne Noyer",
      email = "arne.noyer@ostfalia.de",
      phone = "05826 988 61190",
      imagePath = "file:/data/data/de.rogallab.android/app_Images/man_3.png"
   )
   val person4 = Person(
      name = "Jorin Kleimann",
      email = "j.kleimann@ostfalia.de",
      phone = "05826 988 61550",
      imagePath = "file:/data/data/de.rogallab.android/app_Images/man_4.png"
   )
   val person5 = Person(
      id = 5,
      name = "Christian Hollmann",
      email = "ch.hollmann@ostfalia.de",
      phone = "05826 988 61610",
      imagePath = "file:/data/data/de.rogallab.android/app_Images/man_5.png"
   )
   val person6 = Person(
      name = "Frank Dziembowki",
      email = "f.dziembowski@ostfalia.de",
      phone = "05826 988 61420",
   )

   val people = listOf(person1, person2, person3, person4, person5, person6)

   // match properties not hashCodes
   fun areEqual(peopleArgs: List<Person>) : Boolean {
      if(people.size != peopleArgs.size) return false
      var match = 0
      for(person in people) {
         for(p in peopleArgs) {
            if(person.isEqual(p)) match++
         }
      }
      return match == people.size
   }

}
