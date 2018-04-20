package de.heilsen.ganzhornfest.presenter;


import java.lang.ref.WeakReference;

public class Presenter<T extends Presenter.View> {
    private WeakReference<T> viewRef;

    public void setView(T view) {
        this.viewRef = new WeakReference<>(view);
    }

    public T getView() {
        return viewRef.get();
    }

    public interface View {
        void showLoading();

        void hideLoading();
    }
}
