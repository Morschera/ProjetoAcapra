package br.edu.unifebe.projeto20.listitems

import br.edu.unifebe.projeto20.Model.Pets
import br.edu.unifebe.projeto20.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PetsListProvider {

    private val _petsList = MutableStateFlow<MutableList<Pets>>(mutableListOf())
    private val petsListFlow: StateFlow<MutableList<Pets>> = _petsList

    fun getPets(): Flow<MutableList<Pets>> {
        val list: MutableList<Pets> = mutableListOf(
            Pets(
                imgPets = R.drawable.Barto,
                nome = "Bartô",
                descricao = "Bartô foi resgatado junto com seus 4 irmãos e sua mamãe Angel. Estavam\n" +
                        "em situação de rua, e viviam em uma casa improvisada de plástico. Bartô foi o único que\n" +
                        "sobrou dos seus irmãos.\n" +
                        "É um filhote com muito amor e energia!\n" +
                        "Negativo para FIV e FELV."
            ),

            Pets(
                imgPets = R.drawable.Branca,
                nome = "Branca",
                descricao = "Branca foi retirada de maus tratos, era usada de forma indevida para caça. É\n" +
                        "muito dócil, querida e brincalhona, mas apresenta um certo grau de medo.\n" +
                        "Precisa de um lar com muito amor, carinho e paciência."
            ),

            Pets(
                imgPets = R.drawable.Cleo,
                nome = "Cléo",
                descricao = "Cléo (filha) e Glória (mãe), foram encontradas abandonadas na BR.\n" +
                        "São muito dóceis, amáveis e ativas, apesar da idade mais adulta, não apresentam\n" +
                        "problemas de saúde e são muito brincalhonas.\n" +
                        "Precisam de um lar com muito amor, carinho e espaço, pois são duas cachorras grandes."
            ),

            Pets(
                imgPets = R.drawable.Difusor,
                nome = "Difusor",
                descricao = "Difusor é um gatinho macho, que foi encontrado em uma fábrica de peças\n" +
                        "automotivas (por isso o nome), chegou em estado caquético, com extrema magreza. Aos\n" +
                        "poucos foi se recuperando e hoje é um jovem 100% saudável e cheio de amor!\n" +
                        "Se dá bem com outros animais (cães e gatos).\n" +
                        "Negativo para FIV e FELV."
            ),

            Pets(
                imgPets = R.drawable.Luma,
                nome = "Luma",
                descricao = "Luma vivia nas ruas com seu irmão Juca. Ele foi adotado, mas ela ainda\n" +
                        "segue esperando seu tão sonhado lar.\n" +
                        "Luma é puro amor e calmaria! Precisa de um lar com muito amor e carinho."
            ),

            Pets(
                imgPets = R.drawable.Luzia,
                nome = "Luzia",
                descricao = "Luzia chegou até nós através de um pedido de ajuda, onde a pessoa que\n" +
                        "pediu ajuda relatava que tinha um “monstro” em sua casa. Quando fomos resgatar,\n" +
                        "descobrimos que esse “monstro”, na verdade é uma linda princesa assustada e muito\n" +
                        "carinhosa!\n" +
                        "Luzia é FELV + por isso precisa ser a única filha felina, ou ter outros irmãozinhos que\n" +
                        "também tenham FELV+."
            ),

            Pets(
                imgPets = R.drawable.Maia,
                nome = "Maia",
                descricao = "Maia foi encontrada junto com sua irmã, Mavie, abandonada em Brusque.\n" +
                        "Foram resgatadas e agora Maia precisa de um lar com muito espaço, amor e carinho. É\n" +
                        "super carinhosa e protetora!"
            ),

            Pets(
                imgPets = R.drawable.Mavie,
                nome = "Mavie",
                descricao = "Mavie foi encontrada junto com sua irmã, Maia, abandonada em Brusque.\n" +
                        "Foram resgatadas e agora Mavie precisa de um lar com muito espaço, amor e carinho. É\n" +
                        "super carinhosa e protetora!"
            ),

            Pets(
                imgPets = R.drawable.Mimi,
                nome = "Mimi",
                descricao = "Mimi foi abandonada em uma residência, viveu um tempo na rua até ser\n" +
                        "resgatada. É uma gatinha muito amorosa e carente!.\n" +
                        "Negativa para FIV e FELV."
            )
        )
        _petsList.value = list
        return petsListFlow
    }
}
