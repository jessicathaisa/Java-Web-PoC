package br.com.viktor.javawebpoc.validation;

import org.springframework.util.StringUtils;
import org.springframework.validation.AbstractBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import br.com.viktor.javawebpoc.entity.base.AbstractEntity;
import br.com.viktor.javawebpoc.l10n.MessageKey;
import br.com.viktor.javawebpoc.validation.util.CustomObjectError;
import br.com.viktor.javawebpoc.validation.util.ErrorCode;

public abstract class AbstractValidator<T extends AbstractEntity> implements Validator {

	private Class<T> targetClass;
	private Errors errors;

	public AbstractValidator(Class<T> targetClass) {
		this.targetClass = targetClass;
	}

	protected Errors getErrors() {
		return errors;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return targetClass.isAssignableFrom(arg0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void validate(Object arg0, Errors errors) {
		this.errors = errors;

		validate((T) arg0);
	}

	protected void Required(String field) {
		Object fieldValue = errors.getFieldValue(field);
		if (fieldValue == null || !StringUtils.hasText(fieldValue.toString())) {
			addError(new CustomObjectError(ErrorCode.REQUIRED, field,
					MessageKey.NAME, MessageKey.VALIDATION_FIELD_REQUIRED));
		}
	}

	protected abstract void validate(T entity);

	protected void addError(ObjectError error) {
		if (this.getErrors() instanceof AbstractBindingResult)
			((AbstractBindingResult) this.getErrors()).addError(error);
	}
}
