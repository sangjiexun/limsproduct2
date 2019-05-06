package net.zjcclims.datasource;


import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


/**
  * Created by Administrator on 2015/11/25.
  */
public class DynamicDataSource extends AbstractRoutingDataSource {
 @Override
 protected Object determineCurrentLookupKey() {
  String d = DataSourceHolder.getDataSource();
  return d;
 }
}
