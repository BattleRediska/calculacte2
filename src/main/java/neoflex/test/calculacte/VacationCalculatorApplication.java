package neoflex.test.calculacte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

@SpringBootApplication
@RestController
public class VacationCalculatorApplication {
	Set<LocalDate> holidays = new HashSet<>();

	public VacationCalculatorApplication(){
		File file = new File("holidays.txt");
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (Objects.equals(line, "")) {
					continue;
				}
				holidays.add(LocalDate.parse(line));
			}
		} catch (IOException e) {
			System.err.println("Ошибка при работе с файлом: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(VacationCalculatorApplication.class, args);
	}

	@GetMapping("/calculacte")
	public String calculacte(
			@RequestParam(value = "averageSalaryPerYear") double averageSalaryPerYear,
			@RequestParam(value = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(value = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
	) {
		double averageDailyEarnings = averageSalaryPerYear / 12 / 29.3; // 29.3 - среднее количество дней в месяце по трудовому кодексу
		double result = averageDailyEarnings * countWorkDays(startDate, endDate);
		return String.format("%f", result);
	}

	private int countWorkDays(LocalDate startDate, LocalDate endDate) {
		int workDays = 0;
		LocalDate currentDate = startDate;
		while (!currentDate.isAfter(endDate)) {
			if (!isWeekend(currentDate)&&!isHoliday(currentDate)) {
				workDays++;
			}
			currentDate = currentDate.plusDays(1);
		}
		return workDays;
	}

	private boolean isWeekend(LocalDate date) {
		return date.getDayOfWeek().getValue() >= 6;
	}

	private boolean isHoliday(LocalDate date) {
		return holidays.contains(date);
	}
}