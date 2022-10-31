package onboarding;

import java.util.List;

class Problem1 {
    private static final int FIRST_PAGE_NUMBER = 1;
    private static final int LAST_PAGE_NUMBER = 400;
    private static final int TIE = 0;
    private static final int POBI_WIN = 1;
    private static final int CRONG_WIN = 2;
    private static final int EXCEPTION = -1;

    public static int solution(List<Integer> pobi, List<Integer> crong) {
        if (checkInvalidPage(pobi) || checkInvalidPage(crong)) {
            return EXCEPTION;
        }
        int pobiResult = getMaxResult(pobi);
        int crongResult = getMaxResult(crong);
        return getWinner(pobiResult, crongResult);
    }

    private static int getMaxResult(List<Integer> pages) {
        int leftResult = findMaxFromOnePage(pages.get(0));
        int rightResult = findMaxFromOnePage(pages.get(1));
        return Math.max(leftResult, rightResult);
    }

    private static int findMaxFromOnePage(Integer page) {
        int addMaxNum = getMaxAddNum(page);
        int multipleMaxNum = getMaxMultipleNum(page);
        return Math.max(addMaxNum, multipleMaxNum);
    }

    private static int getMaxAddNum(Integer page) {
        int addMaxNum = 0;
        while ( page > 0 ) {
            int i = page % 10;
            addMaxNum += i;
            page /= 10;
        }
        return addMaxNum;
    }

    private static int getMaxMultipleNum(Integer page) {
        int multipleMaxNum = 1;
        while ( page > 0 ) {
            int i = page % 10;
            multipleMaxNum *= i;
            page /= 10;
        }
        return multipleMaxNum;
    }

    private static int getWinner(Integer pobiMaxNum, Integer crongMaxNum) {
        if (pobiMaxNum.equals(crongMaxNum)) {
            return TIE;
        }
        if (pobiMaxNum > crongMaxNum) {
            return POBI_WIN;
        }
        return CRONG_WIN;
    }

    private static boolean checkInvalidPage(List<Integer> pages) {
        return !isRightPageRange(pages.get(0), pages.get(1)) || !isValidPageFormat(pages.get(0), pages.get(1));
    }

    private static boolean isRightPageRange(Integer leftPage, Integer rightPage) {
        return leftPage > FIRST_PAGE_NUMBER || rightPage < LAST_PAGE_NUMBER;
    }

    private static boolean isValidPageFormat(Integer leftPage, Integer rightPage) {
        return leftPage < rightPage && (rightPage - leftPage) == 1;
    }
}