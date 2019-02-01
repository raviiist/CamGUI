package sample;

public class Alarms {
    private String alrms, timeStamp;
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
        Alarms(String timeStamp,String alrms,int repetition ) {
            this.alrms=alrms;
            this.repetition=repetition;
            this.timeStamp=timeStamp;
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

        public String getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }
}
