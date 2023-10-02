package hu.cubix.hr.zpalvolgyi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "employee")
@Component
public class EmployeeConfigurationProperties {

    private Service service;

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public static class Service{

        private Smart smart;

        public Smart getSmart() {
            return smart;
        }

        public void setSmart(Smart smart) {
            this.smart = smart;
        }

        public static class Smart{
            private List<Double> yearLimits;
            private List<Integer> raisePercentages;

            public List<Double> getYearLimits() {
                return yearLimits;
            }

            public void setYearLimits(List<Double> yearLimits) {
                this.yearLimits = yearLimits;
            }

            public List<Integer> getRaisePercentages() {
                return raisePercentages;
            }

            public void setRaisePercentages(List<Integer> raisePercentages) {
                this.raisePercentages = raisePercentages;
            }
        }
    }
}
