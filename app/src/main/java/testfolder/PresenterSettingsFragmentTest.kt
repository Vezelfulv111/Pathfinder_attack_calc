import com.pathfinder.attackcalc.fragments.SettingsFragment
import com.pathfinder.attackcalc.model.Model
import com.pathfinder.attackcalc.presenters.PresenterSettingsFragment
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test


class PresenterSettingsFragmentTest {
    private var someView = SettingsFragment()
    private val model = Model()
    private val presenter = PresenterSettingsFragment(someView, model)

    @Test
    fun presenterSettingsFragment_enableAttackSwitch_ReturnsFalse() {
        assertFalse(presenter.enableAttackSwitch(false, 100))
    }

    @Test
    fun presenterSettingsFragment_enableAttackSwitch_ReturnsTrue() {
        assertTrue(presenter.enableAttackSwitch(false, 1))
    }


}