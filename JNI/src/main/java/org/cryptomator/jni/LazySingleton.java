package org.cryptomator.jni;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

class LazySingleton<T> implements Supplier<T> {

	private final AtomicReference<T> instance = new AtomicReference<>();
	private final Supplier<T> factory;

	public LazySingleton(Supplier<T> factory) {
		this.factory = factory;
	}

	@Override
	public T get() {
		final T existing = instance.get();
		if (existing != null) {
			return existing;
		} else {
			return instance.updateAndGet(currentValue -> {
				if (currentValue == null) {
					return factory.get();
				} else {
					return currentValue;
				}
			});
		}
	}

}