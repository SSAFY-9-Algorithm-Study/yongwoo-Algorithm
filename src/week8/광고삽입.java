import java.util.*;

class Solution {
    // 문자열 시간을 int로 변환(HH:MM:SS -> 시간 * 3600 + 분 * 60 + 초)
    int timeStrToInt(String time) {
        int hour = Integer.parseInt(time.substring(0, 2));
        int minute = Integer.parseInt(time.substring(3, 5));
        int second = Integer.parseInt(time.substring(6, 8));
        
        return hour * 3600 + minute * 60 + second;
    }
    
    // 숫자 시간을 string으로 변환(시간 * 3600 + 분 * 60 + 초 -> HH:MM:SS)
    String timeIntToStr(int time) {
        String hour = time / 3600 + "";
        if (hour.length() < 2) hour = "0" + hour;
        
        time %= 3600;
        
        String minute = time / 60 + "";
        if (minute.length() < 2) minute = "0" + minute;
        
        time %= 60;
        
        String second = time + "";
        if (second.length() < 2) second = "0" + second;
        
        return hour + ":" + minute + ":" + second;
    }
    
    public String solution(String play_time, String adv_time, String[] logs) {
        // 시청자 시청 시작점, 종료점 저장
        Map<Integer, Integer> viewerMap = new HashMap<>(); // 시청자 재생시간 시작, 종료 지점
        for (int i = 0; i < logs.length; i++) {
            int startTime = timeStrToInt(logs[i].substring(0, 8));
            int endTime = timeStrToInt(logs[i].substring(9, 17));
            
            if (!viewerMap.containsKey(startTime)) viewerMap.put(startTime, 0);
            if (!viewerMap.containsKey(endTime)) viewerMap.put(endTime, 0);
            
            viewerMap.put(startTime, viewerMap.get(startTime) + 1);
            viewerMap.put(endTime, viewerMap.get(endTime) - 1);
        }
        
        // 전체 재생시간 int로 변환, 누적합 배열 생성
        int playTime = timeStrToInt(play_time);
        long[] playArr = new long[playTime + 1]; // 시청자 재생시간 누적합
        
        // 각 인덱스에서 시청자가 있는 경우 누적합
        int viewCnt = 0;
        if (viewerMap.containsKey(0)) viewCnt = viewerMap.get(0);
        playArr[0] = viewCnt;
        
        for (int i = 1; i <= playTime; i++) {
            if (viewerMap.containsKey(i)) viewCnt += viewerMap.get(i);
            
            playArr[i] = playArr[i - 1] + viewCnt;
        }
        
        // 광고 시간의 종료시간에서 누적합 - (시작시간-1)에서 누적합 == 누적 시청시간
        int advTime = timeStrToInt(adv_time);
        
        int res = 0; // 출력 결과 int값
        long maxTotalViewTime = playArr[advTime]; // 최대 누적 시청시간
        
        for (int i = 1; i <= playTime - advTime + 1; i++) {
            long totalViewTime = playArr[advTime + i - 1] - playArr[i - 1];
            
            if (totalViewTime > maxTotalViewTime) {
                res = i;
                maxTotalViewTime = totalViewTime;
            }
        }
        
        String answer = timeIntToStr(res);
        
        return answer;
    }
}
