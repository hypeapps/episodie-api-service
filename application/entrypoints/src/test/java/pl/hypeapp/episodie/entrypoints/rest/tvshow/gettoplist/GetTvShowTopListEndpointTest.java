package pl.hypeapp.episodie.entrypoints.rest.tvshow.gettoplist;

import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pl.hypeapp.core.entity.database.TvShowLocal;
import pl.hypeapp.core.usecase.tvshow.toplist.gettoplist.GetTvShowTopListUseCase;
import pl.hypeapp.core.usecase.tvshow.toplist.gettoplist.ResourceNotFoundException;
import pl.hypeapp.episodie.entrypoints.rest.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

public class GetTvShowTopListEndpointTest {

    private GetTvShowTopListUseCase getTvShowTopListUseCase = mock(GetTvShowTopListUseCase.class);

    private GetTvShowTopListEndpoint getTvShowTopListEndpoint = new GetTvShowTopListEndpoint(getTvShowTopListUseCase);

    @Test
    public void shouldGetTvShowTopList() throws Exception {
        Pageable pageable = new PageRequest(0, 3);
        List<TvShowLocal> tvShows = new ArrayList<>();
        tvShows.add(new TvShowLocal());
        tvShows.add(new TvShowLocal());
        tvShows.add(new TvShowLocal());
        Page<TvShowLocal> tvShowLocalPage = new PageImpl<>(tvShows, pageable, pageable.getPageSize());

        when(getTvShowTopListUseCase.getTopList(pageable)).thenReturn(tvShowLocalPage);

        getTvShowTopListEndpoint.getTopList(pageable);

        verify(getTvShowTopListUseCase, times(1)).getTopList(pageable);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowsException() throws Exception {
        Pageable pageable = new PageRequest(0, 3);
        ResourceNotFoundException exception = new ResourceNotFoundException();

        when(getTvShowTopListUseCase.getTopList(pageable)).thenThrow(exception);

        getTvShowTopListUseCase.getTopList(pageable);

        assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() -> getTvShowTopListUseCase.getTopList(pageable));
        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> getTvShowTopListEndpoint.getTopList(pageable));
    }

}