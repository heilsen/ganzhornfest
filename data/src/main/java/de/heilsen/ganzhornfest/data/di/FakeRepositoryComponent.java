package de.heilsen.ganzhornfest.data.di;

import dagger.Component;

@RepositoryScope
@Component(modules = FakeRepositoryModule.class)
public interface FakeRepositoryComponent extends RepositoryComponent {
}
