package co.istad.ecommerce;

import co.istad.ecommerce.auditing.Book;
import co.istad.ecommerce.auditing.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EnableJpaAuditing
@RequiredArgsConstructor
@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class IteEcommerceApplication implements CommandLineRunner {

	private final BookRepository bookRepository;

	public static void main(String[] args) {
		SpringApplication.run(IteEcommerceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Book book = new Book();
		book.setName("Mindset");
		book.setDescription("Build strong mindset");
		bookRepository.save(book);

	}
}
