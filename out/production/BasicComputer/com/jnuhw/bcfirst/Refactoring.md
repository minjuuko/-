* MVC 도입
  * AssemblerController class
  * Assembler class
    * compile -> parseFirstPass, parseSecondPass
    * compiled -> binaryInstruction

  * OutputView class
    * 프로그램이 실행 중 오류로 인해 종료되었습니다.
      -> printErrorAnnouncement()
    * Line " + lc + " 에서 알 수 없는 Instruction을 발견 강제 종료 합니다. \n문제가 된 Instruction: { " + command + " } || 전체 문장 : " + command + " \n"
      -> printUnknownInstructionError()
  * isMri, isNotMri -> enum 으로 간소화 

* 불필요한 주석 제거, 주석으로 코드를 설명해야지 컴퓨터구조를 설명해선 안된다고 생각함
* 일반 함수 주석 -> javadoc 표준 주석
* Computer 단어 사용 -> Assembler
* BasicComputer.java -> Application.java
* SymbolTable -> Label
  * label -> name
  * lcCount -> lc


* BusSystem
  * Memory -> Memory, AC, AR, ...





### Implement task
* dec, hex instruction
* get indirection code from enum
* parse indirect 


* InputView 로 명령어 입력받기