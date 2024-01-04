import java.util.ArrayList;

public class lcs{
	
	private ArrayList<Keyword> keywords;
	private String y;

	public lcs(ArrayList<Keyword>keywords,String y) {
		this.keywords = keywords;
		this.y = y;
	}
	
    public String rank() {
        int maxValue = 0;
        String maxKeyword = null; // Using a String to store the keyword with the longest LCS

        for (Keyword keyword : keywords) {
            String x = keyword.name;
            int lcsLength = calculateLCS(x, y);

            if (lcsLength > maxValue) {
                maxValue = lcsLength;
                maxKeyword = keyword.name;
            }
        }

        // Return null or an appropriate value if no common subsequence is found
        return maxValue == 0 ? null : maxKeyword;
    }

    // Helper method to calculate LCS length
    private int calculateLCS(String x, String y) {
        int len1 = x.length();
        int len2 = y.length();
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (x.charAt(i - 1) == y.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[len1][len2];
    }
}