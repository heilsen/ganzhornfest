package de.heilsen.ganzhornfest.di

import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.mockk
import io.mockk.verify
import timber.log.Timber

// CrashlyticsTree.log() and isLoggable() both override protected Timber.Tree members, which are
// not directly callable from outside the class. Routing through Timber.plant()/tag() exercises the
// real dispatch path instead of relying on reflection, and that path runs isLoggable() first.
class CrashlyticsTreeTest :
    DescribeSpec({
        afterEach { Timber.uprootAll() }

        describe("CrashlyticsTree") {
            it("ignores VERBOSE logs") {
                val crashlytics = mockk<FirebaseCrashlytics>()
                Timber.plant(CrashlyticsTree(crashlytics))
                Timber.tag("tag").v("message")
                verify(exactly = 0) { crashlytics.log(any()) }
            }

            it("ignores DEBUG logs") {
                val crashlytics = mockk<FirebaseCrashlytics>()
                Timber.plant(CrashlyticsTree(crashlytics))
                Timber.tag("tag").d("message")
                verify(exactly = 0) { crashlytics.log(any()) }
            }

            it("ignores INFO logs") {
                val crashlytics = mockk<FirebaseCrashlytics>()
                Timber.plant(CrashlyticsTree(crashlytics))
                Timber.tag("tag").i("message")
                verify(exactly = 0) { crashlytics.log(any()) }
            }

            it("logs WARN messages as breadcrumbs") {
                val crashlytics = mockk<FirebaseCrashlytics>(relaxed = true)
                Timber.plant(CrashlyticsTree(crashlytics))
                Timber.tag("tag").w("message")
                verify { crashlytics.log("tag: message") }
            }

            it("logs ERROR messages as breadcrumbs") {
                val crashlytics = mockk<FirebaseCrashlytics>(relaxed = true)
                Timber.plant(CrashlyticsTree(crashlytics))
                Timber.tag("tag").e("message")
                verify { crashlytics.log("tag: message") }
            }

            it("records the throwable when one is present") {
                val crashlytics = mockk<FirebaseCrashlytics>(relaxed = true)
                Timber.plant(CrashlyticsTree(crashlytics))
                val throwable = RuntimeException("boom")
                Timber.tag("tag").e(throwable, "message")
                verify { crashlytics.recordException(throwable) }
            }

            it("logs ASSERT messages as breadcrumbs") {
                val crashlytics = mockk<FirebaseCrashlytics>(relaxed = true)
                Timber.plant(CrashlyticsTree(crashlytics))
                Timber.tag("tag").wtf("message")
                verify { crashlytics.log("tag: message") }
            }

            it("does not record an exception when the throwable is null") {
                val crashlytics = mockk<FirebaseCrashlytics>(relaxed = true)
                Timber.plant(CrashlyticsTree(crashlytics))
                Timber.tag("tag").e("message")
                verify(exactly = 0) { crashlytics.recordException(any()) }
            }
        }
    })
