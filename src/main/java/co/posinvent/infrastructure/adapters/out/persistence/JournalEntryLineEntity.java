package co.posinvent.infrastructure.adapters.out.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "journal_entry_lines")
@Getter @Setter
public class JournalEntryLineEntity {
    @Id private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entry_id", nullable = false)
    private JournalEntryEntity entry;
    @Column(name = "account_id", nullable = false) private UUID accountId;
    @Column(nullable = false) private BigDecimal debit;
    @Column(nullable = false) private BigDecimal credit;
    @Column(columnDefinition = "TEXT") private String description;

    @PrePersist void prePersist() { if (id == null) id = UUID.randomUUID(); }
}
