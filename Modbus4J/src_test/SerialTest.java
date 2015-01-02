import com.serotonin.io.serial.SerialParameters;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.code.RegisterRange;
import com.serotonin.modbus4j.locator.NumericLocator;

public class SerialTest {

	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();
		ModbusFactory factory = new ModbusFactory();
		SerialParameters params = new SerialParameters();
		params.setBaudRate(115200);
		params.setCommPortId("/dev/ttyUSB0");

		ModbusMaster master = factory.createRtuMaster(params);
		master.setRetries(0);

		try {
			master.init();
			for (int i = 0; i < 100; i++) {
				try {
					long estimatedTime = System.currentTimeMillis() - startTime;
					long estimatedTime2 = System.currentTimeMillis() - startTime;
					Object o = master.getValue(new NumericLocator(1,
							RegisterRange.HOLDING_REGISTER, 2,
							DataType.FOUR_BYTE_INT_UNSIGNED_SWAPPED));
					long estimatedTime3 = System.currentTimeMillis() - startTime;
					o = master.getValue(new NumericLocator(1,
							RegisterRange.HOLDING_REGISTER, 2,
							DataType.FOUR_BYTE_INT_UNSIGNED_SWAPPED));
					long estimatedTime4 = System.currentTimeMillis() - startTime;
					System.out.println(o.toString());
					System.out.println(estimatedTime);
					System.out.println(estimatedTime2);
					System.out.println(estimatedTime3);
					System.out.println(estimatedTime4);
				} catch (com.serotonin.modbus4j.exception.ModbusTransportException e) {
					System.out.println("ModbusTransportException");
					master.destroy();
					master.init();
				} finally {

				}
			}
		} catch (Exception e) {
			throw (e);
		} finally {
			master.destroy();
		}
	}
}
