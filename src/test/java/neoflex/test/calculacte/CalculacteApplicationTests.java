package neoflex.test.calculacte;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CalculacteApplicationTests {

	@Autowired
	private VacationCalculatorApplication app;

	@Test
	void contextLoads() {
	}

	@Test
	public void testCalculacteTest() {
		double averageSalaryPerYear = 1200000;
		LocalDate startDate = LocalDate.parse("2025-05-01");
		LocalDate endDate = LocalDate.parse("2025-05-11");

		assertEquals("13651,877133", app.calculacte(averageSalaryPerYear, startDate, endDate));
	}

}
