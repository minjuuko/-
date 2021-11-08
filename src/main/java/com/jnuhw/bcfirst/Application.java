package com.jnuhw.bcfirst;

import com.jnuhw.bcfirst.controller.AssemblerController;
import com.jnuhw.bcfirst.domain.Utility;
import com.jnuhw.bcfirst.view.InputView;

import java.util.*;

public class Application {

    // Main Method.
    public static void main(String[] args) {
        // 6-5 Code
//        String[] addingTwoVariableProgram = new String[]{
//                "ORG 1",
//                "LDA A",
//                "ADD B",
//                "STA C",
//                "HLT",
//                "A, DEC 83",
//                "B, DEC -63",
//                "C, DEC 0",
//                "END"
//        };

        // cma 만 고치면 되네요.
        // 이거 고쳐야하니까 이부분 해결해서 여기다가 푸쉬하면 될거같아요.
        // ~ 연산자가 단순히 1의 보수긴한데, 문제가 인트가 32비트에요. 그럼 보수화하면 에이씨 16비트의 보수랑 다르게
        // 1이 엄청 많이 생기겠죠? 이부분을 해결하셔야 해요. 보수화 -> 필요없는 1 삭제
        // 1. 2의 보수화된 걸 문자열로 보내고, 필요없는 1에 해당하는 문자열 삭제
        // 2. 숫자의 연산을 통해서 삭제
        // 편한걸루
        // 해서 아래 출력하는 붑누 이용해서 작동 확실히 확인하고 바로 푸쉬하면 저한테 말해주세요
        //int a = ~(0b1001100110011001);
        //System.out.println(Utility.toFormatBinaryString(16, a));
        AssemblerController assemblerController = new AssemblerController();
        List<String> program = InputView.inputAssemblerProgram();
        assemblerController.run(program);
    }
}