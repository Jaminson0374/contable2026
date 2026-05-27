package co.posinvent.application.usecase;

import co.posinvent.application.dto.PriceListRequest;
import co.posinvent.domain.exception.BusinessException;
import co.posinvent.domain.model.PriceList;
import co.posinvent.domain.repository.PriceListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceListUseCaseTest {

    @Mock
    private PriceListRepository repository;

    private PriceListUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new PriceListUseCase(repository);
    }

    @Test
    void create_rejectsDuplicateCodeBeforePersistence() {
        when(repository.existsByCode("MAYORISTA")).thenReturn(true);

        assertThatThrownBy(() -> useCase.create(new PriceListRequest("MAYORISTA", "Mayorista", null)))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo("DUPLICATE_CODE");

        verify(repository, never()).save(any());
    }

    @Test
    void update_persistsEditedFieldsKeepingState() {
        var id = UUID.randomUUID();
        var createdAt = OffsetDateTime.now().minusDays(3);
        var existing = new PriceList(id, "BASE", "Lista base", "Anterior", true, createdAt);

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.existsByCodeAndIdNot("VIP", id)).thenReturn(false);
        when(repository.existsByNameAndIdNot("Lista VIP", id)).thenReturn(false);
        doAnswer(invocation -> invocation.getArgument(0)).when(repository).save(any(PriceList.class));

        var response = useCase.update(id, new PriceListRequest("VIP", "Lista VIP", "Nueva"));

        var captor = ArgumentCaptor.forClass(PriceList.class);
        verify(repository).save(captor.capture());

        assertThat(captor.getValue()).isEqualTo(new PriceList(id, "VIP", "Lista VIP", "Nueva", true, createdAt));
        assertThat(response.id()).isEqualTo(id);
        assertThat(response.code()).isEqualTo("VIP");
        assertThat(response.name()).isEqualTo("Lista VIP");
        assertThat(response.description()).isEqualTo("Nueva");
        assertThat(response.active()).isTrue();
    }

    @Test
    void deactivate_marksPriceListAsInactive() {
        var id = UUID.randomUUID();
        var createdAt = OffsetDateTime.now().minusDays(1);
        var existing = new PriceList(id, "VIP", "Lista VIP", null, true, createdAt);

        when(repository.findById(id)).thenReturn(Optional.of(existing));

        useCase.deactivate(id);

        var captor = ArgumentCaptor.forClass(PriceList.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue()).isEqualTo(new PriceList(id, "VIP", "Lista VIP", null, false, createdAt));
    }
}
