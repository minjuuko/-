* MVC 도입
  * AssemblerController class
  * Assembler class
    * compile -> parseFirstPass, parseSecondPass
    * compiled -> binaryInstruction
  * InputView, OutputView class
  * isMri, isNotMri -> enum 으로 간소화 

* 불필요한 주석 제거, 주석으로 코드를 설명해야지 컴퓨터구조를 설명해선 안된다고 생각함
* 일반 함수 주석 -> javadoc 표준 주석
* Computer 단어 사용 -> Assembler
* BasicComputer.java -> Application.java
* SymbolTable -> Label
  * label -> name
  * lcCount -> lc

### Implement task
* ASsembler, 158줄
* dec, hex instruction
* get indirection code from enum
* parse indrect 
* 