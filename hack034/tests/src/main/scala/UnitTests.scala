package com.manning.androidhacks.hack050.tests

import junit.framework.Assert._
import _root_.android.test.AndroidTestCase

class UnitTests extends AndroidTestCase {
  def testPackageIsCorrect {
    assertEquals("com.manning.androidhacks.hack050", getContext.getPackageName)
  }
}