package de.rogallab.android.ui.people

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4

import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import de.rogallab.android.data.database.AppDatabase
import de.rogallab.android.repository.PeopleRepositoryImpl
import de.rogallab.android.utilsTest.AndroidTestMainCoroutineRule
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
class PeopleViewModelTest {

   // REPLACE@ExperimentalCoroutinesApi
   val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

   @get: Rule   // no async execution
   var instantTaskExecutorRule = InstantTaskExecutorRule()

   @get: Rule
   val couroutineRule = AndroidTestMainCoroutineRule()

   private val _context    = InstrumentationRegistry.getInstrumentation().context
   private val _seedPeople = SeedPeople(_context)
   private val _person     = _seedPeople.person1

   private lateinit var _database: AppDatabase
   private lateinit var _peopleDao: PeopleDao
   private lateinit var _peopleRepository: PeopleRepository
   private lateinit var _peopleViewModel: PeopleViewModel

   @Before
   fun setUp() {
      _database = Room.inMemoryDatabaseBuilder(
         _context,
         AppDatabase::class.java
      ) .allowMainThreadQueries()
        .build()
      _peopleDao        = _database.createPeopleDao()
      _peopleRepository = PeopleRepositoryImpl(_peopleDao, testDispatcher)
      _peopleViewModel  = PeopleViewModel(_context, _peopleRepository, testDispatcher)
   }

   @After
   fun tearDown() {
      _database.close()
   }
/*
   @Test
   @Throws(Exception::class)
   fun readTest(): Unit = couroutineRule.runBlockingTest {
      // Arrange
      _peopleDao.insertAll(_testPeople.people)
      // Act
//      _peopleViewModel.read()
      // Assert
//      Truth.assertThat(actual).isNotNull()
//      Truth.assertThat(actual).isInstanceOf(Person::class.java)
//      // assertThat(actual).isEqualTo(_person) = are the hashCodes equal
//      Truth.assertThat(_person.isEqual(actual!!))
   }
   @Test
   @Throws(Exception::class)
   fun readAllTest(): Unit = couroutineRule.runBlockingTest {
      // Arrange
      _peopleDao.insertAll(_testPeople.people)
      // Act

      _peopleViewModel.readAll()
      // Assert
      var actual: List<Person> = _peopleViewModel.liveDataPeople.value ?: mutableListOf()

      assertThat(actual).isNotNull()
      assertThat(actual.size).isEqualTo(6)
      assertTrue(_testPeople.areEqual(actual))


   }
*/
   @Test
   fun writeTest() = couroutineRule.runBlockingTest {
      // Arrange
      _peopleViewModel.person = _person
      _peopleViewModel.setState()
      // Act
      val id = _peopleViewModel.write()
      // Assert
      var actual: Person? = _peopleDao.select(id)
      assertThat(actual).isNotNull()
      assertThat(actual).isInstanceOf(Person::class.java)
      assertThat(_person.isEqual(actual!!))
   }

   @Test
   fun updateTest() = couroutineRule.runBlockingTest {
      // Arrange
      _peopleViewModel.person = _person
      _peopleViewModel.setState()
      val id = _peopleViewModel.write()

      var detailPerson: Person? = _peopleDao.select(id)
      detailPerson?.let {  person ->
        _peopleViewModel.person = person
        _peopleViewModel.setState()
      }
      // Act
      _peopleViewModel.eMailState.value ="albrecht.meissner@huhu.de"
      _peopleViewModel.phoneState.value ="+49 0123 4567 890"
      _peopleViewModel.update(id)

      // Assert
      var actual: Person? = _peopleDao.select(id)
      assertThat(actual).isNotNull()
      assertThat(actual).isInstanceOf(Person::class.java)
      assertThat(_person.isEqual(actual!!))
   }
}