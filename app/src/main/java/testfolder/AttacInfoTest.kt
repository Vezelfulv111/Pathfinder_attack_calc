import com.pathfinder.attackcalc.AttackInfo
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class AttackInfoTest {
    val attInfo = AttackInfo()

    @Test
    fun attackInfoTest_CheckAttackInfoValidity_ReturnsFalse() {
        assertFalse(attInfo.checkAttackInfoValidity(4, attInfo))
    }

    @Test
    fun attackInfoTest_CheckAttackInfoValidity_ReturnsTrue() {
        assertTrue(attInfo.checkAttackInfoValidity(2, attInfo))
    }

    @Test
    fun attackInfoTest_removeAt_ReturnsTrue() {
        assertTrue(attInfo.removeAt(1))
    }

    @Test
    fun attackInfoTest_removeAt_ReturnsFalse() {
        assertFalse(attInfo.removeAt(100))
    }

}