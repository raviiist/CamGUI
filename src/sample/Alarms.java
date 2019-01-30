package sample;

public class Alarms {
    private String alrms;
        private int repetition;

        Alarms() {
            this.alrms="";
        }
        Alarms(String alrms) {
            this.alrms=alrms;
        }
        Alarms(String alrms,int repetition) {
            this.alrms=alrms;
            this.repetition=repetition;
        }

        public String getAlrms() {
            return alrms;
        }

        public void setAlrms(String alrms) {
            this.alrms = alrms;
        }

        public int getRepetition() {
            return repetition;
        }

        public void setRepetition(int repetition) {
            this.repetition = repetition;
        }
}
