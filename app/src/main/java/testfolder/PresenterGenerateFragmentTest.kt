import com.pathfinder.attackcalc.fragments.GenerateFragment
import com.pathfinder.attackcalc.model.Model
import com.pathfinder.attackcalc.presenters.PresenterGenerateFragment
import junit.framework.TestCase.assertNotSame
import junit.framework.TestCase.assertSame
import org.junit.Test


class PresenterGenerateFragmentTest {
    private var someView = GenerateFragment()
    private val model = Model()
    private val presenter = PresenterGenerateFragment(someView, model)

    @Test
    fun presenterGenerateFragment_CheckAttackInfoValidity_ReturnsNotMinusOne() {
        assertNotSame(presenter.diceThrow(1,1), -1)
    }

    @Test
    fun presenterGenerateFragment_CheckAttackInfoValidity_ReturnsMinusOne_Case1() {
        assertSame(presenter.diceThrow(12,10), -1)
    }

    @Test
    fun presenterGenerateFragment_CheckAttackInfoValidity_ReturnsMinusOne_Case2() {
        assertSame(presenter.diceThrow(1,0), -1)
    }

    @Test
    fun presenterGenerateFragment_editModifier_ReturnsError() {
        assertSame(presenter.editModifier(false,10), "error")
    }

    @Test
    fun presenterGenerateFragment_editModifier_ReturnsOk() {
        assertNotSame(presenter.editModifier(false,1), "error")
    }

}