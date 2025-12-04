import com.trin.erp.manufacturing.model.WorkOrder
import com.trin.erp.manufacturing.model.WorkOrderStatus
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ManufacturingTest {

    @Test
    fun `test work order lifecycle`() {
        // Arrange
        val wo = WorkOrder(
            workOrderNumber = "WO-TEST",
            itemId = "item-1",
            itemName = "Test Item",
            quantityToProduce = 10,
            status = WorkOrderStatus.PENDING
        )

        // Act & Assert 1: Start Production
        wo.start()
        assertEquals(WorkOrderStatus.IN_PROGRESS, wo.status)

        // Act & Assert 2: Complete Production
        wo.complete()
        assertEquals(WorkOrderStatus.COMPLETED, wo.status)
    }

    @Test
    fun `test invalid transition throws exception`() {
        val wo = WorkOrder(
            workOrderNumber = "WO-ERR",
            itemId = "item-1",
            itemName = "Test",
            quantityToProduce = 5,
            status = WorkOrderStatus.PENDING
        )

        // Tidak bisa langsung complete tanpa start
        assertFailsWith<IllegalStateException> {
            wo.complete()
        }
    }
}