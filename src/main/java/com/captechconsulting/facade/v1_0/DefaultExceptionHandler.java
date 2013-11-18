package com.captechconsulting.facade.v1_0;

import com.captechconsulting.facade.Versions;
import com.google.common.collect.Maps;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
public class DefaultExceptionHandler {

    @RequestMapping(produces = Versions.V1_0)
    @ExceptionHandler({MissingServletRequestParameterException.class,
            MethodArgumentNotValidException.class, UnsatisfiedServletRequestParameterException.class,
            ServletRequestBindingException.class
    })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    Map handleRequestException(Exception ex) throws IOException {
        Map<String, Object> map = Maps.newHashMap();
        map.put("error", "Request Error");
        map.put("cause", ex.getMessage());
        return map;
    }

    @RequestMapping(produces = Versions.V1_0)
    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    Map handleValidationException(ConstraintViolationException ex) throws IOException {
        Map<String, Object> map = Maps.newHashMap();
        map.put("error", "Validation Failure");
        map.put("violations", convertConstraintViolation(ex.getConstraintViolations()));
        return map;
    }

    @RequestMapping(produces = Versions.V1_0)
    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody
    Map handleValidationException(EntityNotFoundException ex) throws IOException {
        Map<String, Object> map = Maps.newHashMap();
        map.put("error", "Entity Not Found");
        map.put("cause", ex.getMessage());
        return map;
    }

    @RequestMapping(produces = Versions.V1_0)
    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public @ResponseBody
    Map handleDataIntegrityViolationException(DataIntegrityViolationException ex) throws IOException {
        Map<String, Object> map = Maps.newHashMap();
        map.put("error", "Data Integrity Error");
        map.put("cause", ex.getCause().getMessage());
        return map;
    }

/*
    @RequestMapping(produces = Versions.V1_0)
    @ExceptionHandler({DuplicateException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public @ResponseBody
    Map handleDuplicateException(DuplicateException ex) throws IOException {
        Map<String, Object> map = Maps.newHashMap();
        map.put("error", "Data Integrity Error");
        map.put("type", ex.getType());
        map.put("fieldName", ex.getFieldNames());
        return map;
    }
*/

    @RequestMapping(produces = Versions.V1_0)
    @ExceptionHandler({DataAccessException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    Map handleDataAccessException(DataAccessException ex) throws IOException {
        Map<String, Object> map = Maps.newHashMap();
        map.put("error", "Data Error");
        map.put("cause", ex.getCause().getMessage());
        return map;
    }

    @RequestMapping(produces = Versions.V1_0)
    @ExceptionHandler({Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    Map handleUncaughtException(Exception ex) throws IOException {
        Map<String, Object> map = Maps.newHashMap();
        map.put("error", "Unknown Error");
        if (ex.getCause() != null) {
            map.put("cause", ex.getCause().getMessage());
        } else {
            map.put("cause", ex.getMessage());
        }
        return map;
    }

    private Map<String, Map<String, Object>> convertConstraintViolation(Set<ConstraintViolation<?>> constraintViolations) {
        Map<String, Map<String, Object>> result = Maps.newHashMap();
        for (ConstraintViolation constraintViolation : constraintViolations) {
            Map<String, Object> violationMap = Maps.newHashMap();
            violationMap.put("value", constraintViolation.getInvalidValue());
            violationMap.put("type", constraintViolation.getRootBeanClass());
            violationMap.put("message", constraintViolation.getMessage());
            result.put(constraintViolation.getPropertyPath().toString(), violationMap);
        }
        return result;
    }

}
