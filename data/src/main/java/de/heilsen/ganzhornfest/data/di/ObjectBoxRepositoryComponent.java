package de.heilsen.ganzhornfest.data.di;

import dagger.Component;

@RepositoryScope
@Component(modules = ObjectBoxModule.class)
public interface ObjectBoxRepositoryComponent extends RepositoryComponent {
}
