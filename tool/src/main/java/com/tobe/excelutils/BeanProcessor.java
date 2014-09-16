package com.tobe.excelutils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanProcessor {

	protected static final int PROPERTY_NOT_FOUND = -1;
	
	 private static final Map<Class<?>, Object> primitiveDefaults = new HashMap<Class<?>, Object>();

	    static {
	        primitiveDefaults.put(Integer.TYPE, Integer.valueOf(0));
	        primitiveDefaults.put(Short.TYPE, Short.valueOf((short) 0));
	        primitiveDefaults.put(Byte.TYPE, Byte.valueOf((byte) 0));
	        primitiveDefaults.put(Float.TYPE, Float.valueOf(0f));
	        primitiveDefaults.put(Double.TYPE, Double.valueOf(0d));
	        primitiveDefaults.put(Long.TYPE, Long.valueOf(0L));
	        primitiveDefaults.put(Boolean.TYPE, Boolean.FALSE);
	        primitiveDefaults.put(Character.TYPE, Character.valueOf((char) 0));
	    }
	    
	    
	    
	 public <T> T toBean(ExcelResultSet rs, Class<T> type) throws SQLException {

			PropertyDescriptor[] props = this.propertyDescriptors(type);

			List<String> headers = rs.getHeaders();
			int[] columnToProperty = this.mapColumnsToProperties(headers, props);

			return this.createBean(rs, type, props, columnToProperty);
	}

	 
	 public <T> List<T> toBeanList(ExcelResultSet rs, Class<T> type) throws SQLException {
	        List<T> results = new ArrayList<T>();

	        if (!rs.next()) {
	            return results;
	        }

	        PropertyDescriptor[] props = this.propertyDescriptors(type);
	        List<String> headers = rs.getHeaders();
	        int[] columnToProperty = this.mapColumnsToProperties(headers, props);

	        do {
	            results.add(this.createBean(rs, type, props, columnToProperty));
	        } while (rs.next());

	        return results;
	    }
	 
	 
	//下面是功能
	private PropertyDescriptor[] propertyDescriptors(Class<?> c)
			throws SQLException {
		// Introspector caches BeanInfo classes for better performance
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(c);

		} catch (IntrospectionException e) {
			throw new SQLException("Bean introspection failed: "
					+ e.getMessage());
		}

		return beanInfo.getPropertyDescriptors();
	}

	protected int[] mapColumnsToProperties(List<String> rsmd, PropertyDescriptor[] props) throws SQLException {

		int cols = rsmd.size();//这里的rsmd为header
		int[] columnToProperty = new int[cols];
		Arrays.fill(columnToProperty, PROPERTY_NOT_FOUND);

		for (int col = 0; col < cols; col++) {
			String columnName = rsmd.get(col);//从0开始, 数据库结果集从1开始,
			if (null == columnName || 0 == columnName.length()) {
				columnName = "NULL";
			}

			String propertyName = columnName;
			// String propertyName = columnToPropertyOverrides.get(columnName);
			// if (propertyName == null) {
			// propertyName = columnName;
			// }
			for (int i = 0; i < props.length; i++) {

				if (propertyName.equalsIgnoreCase(props[i].getName())) {
					columnToProperty[col] = i;//header顺序为数据读取书序,在这里和bean字段对齐,也可以直接根据字段名字调用反射,不用对齐字段了
					break;
				}
			}
		}

		return columnToProperty;
	}

	private <T> T createBean(ExcelResultSet rs, Class<T> type,
			PropertyDescriptor[] props, int[] columnToProperty)
			throws SQLException {

		T bean = this.newInstance(type);

		for (int i = 0; i < columnToProperty.length; i++) {

			if (columnToProperty[i] == PROPERTY_NOT_FOUND) {
				continue;
			}

			PropertyDescriptor prop = props[columnToProperty[i]];
			Class<?> propType = prop.getPropertyType();

			Object value = null;
			if (propType != null) {
				value = this.processColumn(rs, i, propType);

				if (value == null && propType.isPrimitive()) {
					value = primitiveDefaults.get(propType);
				}
			}

			this.callSetter(bean, prop, value);
		}

		return bean;
	}
	
	private void callSetter(Object target, PropertyDescriptor prop, Object value)
	            throws SQLException {

	        Method setter = prop.getWriteMethod();

	        if (setter == null) {
	            return;
	        }

	        Class<?>[] params = setter.getParameterTypes();
	        try {
	            // convert types for some popular ones
	            if (value instanceof java.util.Date) {
	                final String targetType = params[0].getName();
	                if ("java.sql.Date".equals(targetType)) {
	                    value = new java.sql.Date(((java.util.Date) value).getTime());
	                } else
	                if ("java.sql.Time".equals(targetType)) {
	                    value = new java.sql.Time(((java.util.Date) value).getTime());
	                } else
	                if ("java.sql.Timestamp".equals(targetType)) {
	                    Timestamp tsValue = (Timestamp) value;
	                    int nanos = tsValue.getNanos();
	                    value = new java.sql.Timestamp(tsValue.getTime());
	                    ((Timestamp) value).setNanos(nanos);
	                }
	            } else
	            if (value instanceof String && params[0].isEnum()) {
	                value = Enum.valueOf(params[0].asSubclass(Enum.class), (String) value);
	            }

	            // Don't call setter if the value object isn't the right type
	            if (this.isCompatibleType(value, params[0])) {
	                setter.invoke(target, new Object[]{value});
	            } else {
	              throw new SQLException(
	                  "Cannot set " + prop.getName() + ": incompatible types, cannot convert "
	                  + value.getClass().getName() + " to " + params[0].getName());
	                  // value cannot be null here because isCompatibleType allows null
	            }

	        } catch (IllegalArgumentException e) {
	            throw new SQLException(
	                "Cannot set " + prop.getName() + ": " + e.getMessage());

	        } catch (IllegalAccessException e) {
	            throw new SQLException(
	                "Cannot set " + prop.getName() + ": " + e.getMessage());

	        } catch (InvocationTargetException e) {
	            throw new SQLException(
	                "Cannot set " + prop.getName() + ": " + e.getMessage());
	        }
	  }

	/**
     * ResultSet.getObject() returns an Integer object for an INT column.  The
     * setter method for the property might take an Integer or a primitive int.
     * This method returns true if the value can be successfully passed into
     * the setter method.  Remember, Method.invoke() handles the unwrapping
     * of Integer into an int.
     *
     * @param value The value to be passed into the setter method.
     * @param type The setter's parameter type (non-null)
     * @return boolean True if the value is compatible (null => true)
     */
    private boolean isCompatibleType(Object value, Class<?> type) {
        // Do object check first, then primitives
        if (value == null || type.isInstance(value)) {
            return true;

        } else if (type.equals(Integer.TYPE) && value instanceof Integer) {
            return true;

        } else if (type.equals(Long.TYPE) && value instanceof Long) {
            return true;

        } else if (type.equals(Double.TYPE) && value instanceof Double) {
            return true;

        } else if (type.equals(Float.TYPE) && value instanceof Float) {
            return true;

        } else if (type.equals(Short.TYPE) && value instanceof Short) {
            return true;

        } else if (type.equals(Byte.TYPE) && value instanceof Byte) {
            return true;

        } else if (type.equals(Character.TYPE) && value instanceof Character) {
            return true;

        } else if (type.equals(Boolean.TYPE) && value instanceof Boolean) {
            return true;

        }
        return false;

    }

	protected <T> T newInstance(Class<T> c) throws SQLException {
		try {
			return c.newInstance();

		} catch (InstantiationException e) {
			throw new SQLException("Cannot create " + c.getName() + ": "
					+ e.getMessage());

		} catch (IllegalAccessException e) {
			throw new SQLException("Cannot create " + c.getName() + ": "
					+ e.getMessage());
		}
	}

	protected Object processColumn(ExcelResultSet rs, int index,
			Class<?> propType) throws SQLException {
		if (!propType.isPrimitive() && rs.getObject(index) == null) {
			return null;
		}

		if (propType.equals(String.class)) {
			return rs.getString(index);

		} else if (propType.equals(Integer.TYPE) || propType.equals(Integer.class)) {
			return Integer.valueOf(rs.getInt(index));

		} else {
			return rs.getObject(index);
		}
	}

	
}
