package de.rogallab.android.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import de.rogallab.android.data.database.AppDatabase
import de.rogallab.android.utilsTest.AndroidTestMainCoroutineRule
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class RepositoryTest {

   @get: Rule   // no async execution
   var instantTaskExecutorRule = InstantTaskExecutorRule()

   @get: Rule
   val couroutineRule = AndroidTestMainCoroutineRule()

   private val _context    = InstrumentationRegistry.getInstrumentation().context
   private val _seedPeople = SeedPeople(_context)
   private val _person     = _seedPeople.person

   private lateinit var _database: AppDatabase
   private lateinit var _peopleDao: PeopleDao
   private lateinit var _peopleRepository: PeopleRepository

   @Before
   fun setUp() {
      _database = Room.inMemoryDatabaseBuilder(
         _context,
         AppDatabase::class.java
      )  .allowMainThreadQueries()
         .build()
      _peopleDao = _database.createPeopleDao()
      _peopleRepository = PeopleRepositoryImpl(_peopleDao, TestCoroutineDispatcher())
   }
   @After
   fun tearDown() {
      _database.close()
   }
   @Test
   @Throws(Exception::class)
   fun readByIdTest(): Unit = couroutineRule.runBlockingTest {
      // Arrange
      _peopleRepository.write(_person)
      // Act
      val actual: Person? = _peopleRepository.readById(_person.id)
      // Assert
      assertThat(actual).isNotNull()
      assertThat(actual).isInstanceOf(Person::class.java)
      // assertThat(actual).isEqualTo(_person) = are the hashCodes equal
      assertThat(_person.isEqual(actual!!))
   }
   @Test
   fun readAllTest(): Unit = couroutineRule.runBlockingTest  {
      // Arrange
      _peopleRepository.writeAll(_seedPeople.people)
      // Act
      val actual: List<Person> = _peopleRepository.readAll().getOrAwaitValue()
      // Assert
      assertThat(actual.size).isEqualTo(9)
      assertTrue(_seedPeople.areEqual(actual))
   }
   @Test
   fun writeTest(): Unit = couroutineRule.runBlockingTest  {
      // Act
      _peopleRepository.write(_person)
      // Assert
      val actual: Person? = _peopleDao.select(_person.id)
      assertThat(actual).isNotNull()
      assertThat(actual).isInstanceOf(Person::class.java)
      assertThat(_person.isEqual(actual!!))
   }

   @Test
   fun writeAllTest(): Unit = couroutineRule.runBlockingTest {
      // Act
      _peopleRepository.writeAll(_seedPeople.people)
      // Assert
      val actual: List<Person> = _peopleDao.selectAll().getOrAwaitValue()
      assertThat(actual.size).isEqualTo(9)
      assertTrue(_seedPeople.areEqual(actual))
   }
   @Test
   fun updateTest(): Unit = couroutineRule.runBlockingTest {
      // Arrange
      _peopleRepository.write(_person)
      _person.email = "erika.mustermann@gmail.com"
      _person.phone = "+49 123 4567 890"
      // Act
      var rowsAffected = _peopleRepository.update(_person)
      // Assert
      assertThat(rowsAffected).isEqualTo(1)
      val actual = _peopleDao.select(_person.id)
      assertThat(actual).isNotNull()
      assertThat(_person.isEqual(actual!!))
   }
   @Test
   fun deleteTest(): Unit = couroutineRule.runBlockingTest {
      // Arrange
      _peopleRepository.write(_person)
      // Act
      var rowsAffected = _peopleRepository.remove(_person)
      // Assert
      assertThat(rowsAffected).isEqualTo(1)
      val actual = _peopleDao.select(_person.id)
      assertThat(actual).isNull()
   }
}