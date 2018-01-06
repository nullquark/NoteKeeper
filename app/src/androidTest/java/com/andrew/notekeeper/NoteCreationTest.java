package com.andrew.notekeeper;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.assertion.ViewAssertions.*;

import static org.hamcrest.Matchers.*;

/**
 * Created by andrew
 */
@RunWith(AndroidJUnit4.class)
public class NoteCreationTest {
   static DataManager sDataManager;

   @BeforeClass
   public static void classSetup() throws Exception {
      sDataManager = DataManager.getInstance();
   }

   @Rule
   public ActivityTestRule<NoteListActivity> mNoteListActivityActivityTestRule =
         new ActivityTestRule<NoteListActivity>(NoteListActivity.class);

   @Test
   public  void createNewNote() {
      final CourseInfo course = sDataManager.getCourse("java_lang");
      final String noteTitle = "Test note title";
      final String noteText = "This is the body of a test note";

      onView(withId(R.id.fab)).perform(click());

      onView(withId(R.id.spinner_courses)).perform(click());
      onData(allOf(instanceOf(CourseInfo.class), equalTo(course))).perform(click());
      onView(withId(R.id.spinner_courses)).check(matches(withSpinnerText(containsString(course.getTitle()))));

      onView(withId(R.id.text_note_title)).perform(typeText(noteTitle));
      onView(withId(R.id.text_note_text)).perform(typeText(noteText), closeSoftKeyboard());

      pressBack();

      int noteIndex = sDataManager.getNotes().size() - 1;
      NoteInfo compareNote = sDataManager.getNotes().get(noteIndex);

      assertEquals(course, compareNote.getCourse());
      assertEquals(noteTitle, compareNote.getTitle());
      assertEquals(noteText, compareNote.getText());
   }
}