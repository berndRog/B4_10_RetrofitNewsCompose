package de.rogallab.android.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import de.rogallab.android.data.database.AppDatabase
import de.rogallab.android.utilsTest.AndroidTestMainCoroutineRule
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// https://developer.android.com/codelabs/advanced-android-kotlin-training-testing-survey#3

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class PeopleDaoTest {

   @get: Rule   // no async execution
   var instantTaskExecutorRule = InstantTaskExecutorRule()

   @get: Rule
   val couroutineRule = AndroidTestMainCoroutineRule()

   private val _context = InstrumentationRegistry.getInstrumentation().context
   private val _seedPeople = SeedPeople(_context)
   private val _person = _seedPeople.person

   private lateinit var _database: AppDatabase
   private lateinit var _peopleDao: PeopleDao

   @Before
   fun setUp() {
      _database = Room.inMemoryDatabaseBuilder(
         _context,
         AppDatabase::class.java
      ).allowMainThreadQueries()
         .build()
      _peopleDao = _database.createPeopleDao()
   }

   @After
   fun tearDown() {
      _database.close()
   }

   @Test
   fun selectTest(): Unit = couroutineRule.runBlockingTest {
      // Arrange
      _person.id = _peopleDao.insert(_person)
      // Act
      val actual: Person? = _peopleDao.select(_person.id)
      // Assert
      assertThat(actual).isNotNull()
      assertThat(actual).isInstanceOf(Person::class.java)
      // assertThat(actual).isEqualTo(_person) = are the hashCodes equal
      assertThat(_person.isEqual(actual!!))
   }
   @Test
   fun selectAllTest(): Unit = couroutineRule.runBlockingTest  {
      // Arrange
      var ids = _peopleDao.insertAll(_seedPeople.people)
      for(i in _seedPeople.people.indices) _seedPeople.people[i].id = ids[i]
      // Act
      val actual: List<Person> = _peopleDao.selectAll().getOrAwaitValue()
      // Assert
      assertThat(actual.size).isEqualTo(9)
      assertTrue(_seedPeople.areEqual(actual))
   }
   @Test
   fun selectFailsIdNotFound(): Unit = runBlocking {
      // Arrange
      var ids = _peopleDao.insertAll(_seedPeople.people)
      for(i in _seedPeople.people.indices) _seedPeople.people[i].id = ids[i]
      // Act
      val actualPerson: Person? = _peopleDao.select(-1)
      // Assert
      assertThat(actualPerson).isEqualTo(null)
   }
   @Test
   fun insertTest(): Unit = couroutineRule.runBlockingTest  {
      // Act
      _person.id = _peopleDao.insert(_person)
      // Assert
      val actual: Person? = _peopleDao.select(_person.id)
      assertThat(actual).isNotNull()
      assertThat(actual).isInstanceOf(Person::class.java)
      assertThat(_person.isEqual(actual!!))
   }
   @Test
   fun insertAllTest(): Unit = couroutineRule.runBlockingTest {
      // Act
      var ids =_peopleDao.insertAll(_seedPeople.people)
      for(i in _seedPeople.people.indices) _seedPeople.people[i].id = ids[i]
      // Assert
      val actual: List<Person> = _peopleDao.selectAll().getOrAwaitValue()
      assertThat(actual).isNotNull()
      assertTrue(_seedPeople.areEqual(actual))
   }
   @Test
   fun updateTest(): Unit = couroutineRule.runBlockingTest {
      // Arrange
      _person.id = _peopleDao.insert(_person)
      // Act
      _person.email = "erika.mustermann@gmail.com"
      _person.phone = "+49 0987 6543 210"
      val rowsAffected = _peopleDao.update(_person)
      // Assert
      assertThat(rowsAffected).isEqualTo(1)
      val actual = _peopleDao.select(_person.id)
      assertThat(actual).isNotNull()
      assertThat(_person.isEqual(actual!!))
   }
   @Test
   fun deleteTest(): Unit = couroutineRule.runBlockingTest {
      // Arrange
      _person.id = _peopleDao.insert(_person)
      // Act
      val rowsAffected = _peopleDao.delete(_person)
      // Assert
      assertThat(rowsAffected).isEqualTo(1)
      val actual = _peopleDao.select(_person.id)
      assertThat(actual).isNull()
   }
}
