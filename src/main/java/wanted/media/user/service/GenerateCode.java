package wanted.media.user.service;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GenerateCode {
    private int codeLength = 6; //6자리 코드
    private final char[] characterTable = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

    public String codeGenerate() {
        Random random = new Random(System.currentTimeMillis());
        int tableLength = characterTable.length;
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < codeLength; i++) {
            code.append(characterTable[random.nextInt(tableLength)]);
        }
        return code.toString();
    }
}
