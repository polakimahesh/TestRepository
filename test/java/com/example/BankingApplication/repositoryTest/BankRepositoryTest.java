package com.example.BankingApplication.repositoryTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import com.example.BankingApplication.bank.Bank;
import com.example.BankingApplication.bank.BankRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@DataJpaTest

public class BankRepositoryTest {

    @Autowired
    private BankRepository bankRepository;

    @Test
    public void testSave() {
        Bank bank = new Bank();
        bank.setName("Bank of America");
        bank.setIfscCode("BOFA0AXXXX");
        bank.setBranch("New York");

        Bank savedBank = bankRepository.save(bank);

        assertThat(savedBank.getId()).isNotNull();
        assertThat(savedBank.getName()).isEqualTo("Bank of America");
        assertThat(savedBank.getIfscCode()).isEqualTo("BOFA0AXXXX");
        assertThat(savedBank.getBranch()).isEqualTo("New York");
    }

    @Test
    public void testFindAll() {
        Bank bank1 = new Bank();
        bank1.setName("Bank of America");
        bank1.setIfscCode("BOFA0AXXXX");
        bank1.setBranch("New York");

        Bank bank2 = new Bank();
        bank2.setName("Chase Bank");
        bank2.setIfscCode("CHAS0XXXXX");
        bank2.setBranch("Chicago");

        bankRepository.save(bank1);
        bankRepository.save(bank2);

        List<Bank> banks = bankRepository.findAll();

        assertThat(banks).hasSize(2);
        assertThat(banks).contains(bank1, bank2);
    }

    @Test
    public void testFindById() {
        Bank bank = new Bank();
        bank.setName("Bank of America");
        bank.setIfscCode("BOFA0AXXXX");
        bank.setBranch("New York");

        bankRepository.save(bank);

        Optional<Bank> foundBank = bankRepository.findById(bank.getId());

        assertThat(foundBank.isPresent()).isTrue();
        assertThat(foundBank.get().getName()).isEqualTo("Bank of America");
        assertThat(foundBank.get().getIfscCode()).isEqualTo("BOFA0AXXXX");
        assertThat(foundBank.get().getBranch()).isEqualTo("New York");
    }

    @Test
    public void testUpdate() {
        Bank bank = new Bank();
        bank.setName("Bank of America");
        bank.setIfscCode("BOFA0AXXXX");
        bank.setBranch("New York");

        bankRepository.save(bank);

        bank.setBranch("Chicago");

        Bank updatedBank = bankRepository.save(bank);

        assertThat(updatedBank.getBranch()).isEqualTo("Chicago");
    }

    @Test
    public void testDelete() {
        Bank bank = new Bank();
        bank.setName("Bank of America");
        bank.setIfscCode("BOFA0AXXXX");
        bank.setBranch("New York");

        bankRepository.save(bank);

        bankRepository.delete(bank);

        Optional<Bank> foundBank = bankRepository.findById(bank.getId());

        assertThat(foundBank.isPresent()).isFalse();
    }
}
