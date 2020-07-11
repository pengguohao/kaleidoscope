package com.pgh.kaleidoscope.core.tool.convert;

import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.lang.Nullable;
import org.springframework.util.StringValueResolver;

/**
 * 类型 转换 服务，添加了 IEnum 转换
 *
 * @author L.cm
 */
public class KaleidoscopeConversionService extends ApplicationConversionService {
	@Nullable
	private static volatile KaleidoscopeConversionService SHARED_INSTANCE;

	public KaleidoscopeConversionService() {
		this(null);
	}

	public KaleidoscopeConversionService(@Nullable StringValueResolver embeddedValueResolver) {
		super(embeddedValueResolver);
		super.addConverter(new EnumToStringConverter());
		super.addConverter(new StringToEnumConverter());
	}

	/**
	 * Return a shared default application {@code ConversionService} instance, lazily
	 * building it once needed.
	 * <p>
	 * Note: This method actually returns an {@link KaleidoscopeConversionService}
	 * instance. However, the {@code ConversionService} signature has been preserved for
	 * binary compatibility.
	 * @return the shared {@code BladeConversionService} instance (never{@code null})
	 */
	public static GenericConversionService getInstance() {
		KaleidoscopeConversionService sharedInstance = KaleidoscopeConversionService.SHARED_INSTANCE;
		if (sharedInstance == null) {
			synchronized (KaleidoscopeConversionService.class) {
				sharedInstance = KaleidoscopeConversionService.SHARED_INSTANCE;
				if (sharedInstance == null) {
					sharedInstance = new KaleidoscopeConversionService();
					KaleidoscopeConversionService.SHARED_INSTANCE = sharedInstance;
				}
			}
		}
		return sharedInstance;
	}

}
