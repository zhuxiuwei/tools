package SmartJacksonTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author zhuxiuwei
 * @date 2025/5/22
 */
public class JacksoParse {
    public static void main(String[] args) throws JsonProcessingException {
        String json = "{\n" +
                "    \"status\": 0,\n" +
                "    \"message\": \"成功\",\n" +
                "    \"data\": {\n" +
                "        \"total\": 9,\n" +
                "        \"items\": [\n" +
                "            {\n" +
                "                \"role\": \"assistant\",\n" +
                "                \"time\": \"2025-05-21 20:57:50\",\n" +
                "                \"message\": \"很抱歉，文档中没有你想要的答案。\",\n" +
                "                \"like\": 0,\n" +
                "                \"debugInfo\": {},\n" +
                "                \"recallCount\": null,\n" +
                "                \"recallInfo\": {\n" +
                "                    \"docRecallItems\": [\n" +
                "                        {\n" +
                "                            \"qaType\": null,\n" +
                "                            \"recallSource\": \"SIMILARITY\",\n" +
                "                            \"recallPattern\": \"HYBRID_RECALL\",\n" +
                "                            \"rerankModel\": \"AVERAGE_RANK\",\n" +
                "                            \"score\": 0.45675835110732665,\n" +
                "                            \"businessName\": \"416092089188358\",\n" +
                "                            \"position\": 1,\n" +
                "                            \"richData\": {\n" +
                "                                \"richMaps\": {},\n" +
                "                                \"source\": {\n" +
                "                                    \"type\": \"KM\",\n" +
                "                                    \"docId\": 1045757620,\n" +
                "                                    \"nodeIds\": [\n" +
                "                                        \"8c5670e771d646199c4134bce74298f6\"\n" +
                "                                    ],\n" +
                "                                    \"docName\": \"常用功能介绍\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            \"textId\": \"416092178948098\",\n" +
                "                            \"text\": \"望知悉\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"qaType\": null,\n" +
                "                            \"recallSource\": \"SIMILARITY\",\n" +
                "                            \"recallPattern\": \"HYBRID_RECALL\",\n" +
                "                            \"rerankModel\": \"AVERAGE_RANK\",\n" +
                "                            \"score\": 0.4348819698575502,\n" +
                "                            \"businessName\": \"416092089188360\",\n" +
                "                            \"position\": 0,\n" +
                "                            \"richData\": {\n" +
                "                                \"richMaps\": {},\n" +
                "                                \"source\": {\n" +
                "                                    \"type\": \"KM\",\n" +
                "                                    \"docId\": 2258634407,\n" +
                "                                    \"nodeIds\": [\n" +
                "                                        \"437bc7cc52564321846581edcb815365\",\n" +
                "                                        \"41427ecbd5cc4525a3e2c3e3358c05b5\"\n" +
                "                                    ],\n" +
                "                                    \"docName\": \"语言和时区偏好设置/Language and timezone perference\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            \"textId\": \"416092178710529\",\n" +
                "                            \"text\": \"\\n\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"qaType\": null,\n" +
                "                            \"recallSource\": \"SIMILARITY\",\n" +
                "                            \"recallPattern\": \"HYBRID_RECALL\",\n" +
                "                            \"rerankModel\": \"AVERAGE_RANK\",\n" +
                "                            \"score\": 0.4119422989958761,\n" +
                "                            \"businessName\": \"421657785237505\",\n" +
                "                            \"position\": 0,\n" +
                "                            \"richData\": {\n" +
                "                                \"richMaps\": {\n" +
                "                                    \"0bd27f1e-5978-4d8e-8259-5b9ba55e81f1\": {\n" +
                "                                        \"id\": \"0bd27f1e-5978-4d8e-8259-5b9ba55e81f1\",\n" +
                "                                        \"name\": \"video1172867616.mp4\",\n" +
                "                                        \"contentType\": \"LINK\",\n" +
                "                                        \"mime\": \"HREF\",\n" +
                "                                        \"data\": \"https://km.sankuai.com/api/file/cdn/2160201196/90843622548?contentType=1\",\n" +
                "                                        \"publicAccess\": false\n" +
                "                                    }\n" +
                "                                },\n" +
                "                                \"source\": {\n" +
                "                                    \"type\": \"KM\",\n" +
                "                                    \"docId\": 2160201196,\n" +
                "                                    \"nodeIds\": [\n" +
                "                                        \"44594708f1a74043bc64815a8b0bd1e9\",\n" +
                "                                        \"9714486b1b6f4c47ac8ff9d131836b46\",\n" +
                "                                        \"29f790ec966c4ca58e818642ff4ec12d\"\n" +
                "                                    ],\n" +
                "                                    \"docName\": \"新版值班讲解\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            \"textId\": \"421657806209025\",\n" +
                "                            \"text\": \"{{0bd27f1e-5978-4d8e-8259-5b9ba55e81f1}}\\n\\n\u200B\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"qaType\": null,\n" +
                "                            \"recallSource\": \"SIMILARITY\",\n" +
                "                            \"recallPattern\": \"HYBRID_RECALL\",\n" +
                "                            \"rerankModel\": \"AVERAGE_RANK\",\n" +
                "                            \"score\": 0.410764561983303,\n" +
                "                            \"businessName\": \"416092089188360\",\n" +
                "                            \"position\": 6,\n" +
                "                            \"richData\": {\n" +
                "                                \"richMaps\": {},\n" +
                "                                \"source\": {\n" +
                "                                    \"type\": \"KM\",\n" +
                "                                    \"docId\": 2258634407,\n" +
                "                                    \"nodeIds\": [\n" +
                "                                        \"3ac5697c885f4fa8adac873f58bbf994\",\n" +
                "                                        \"1446a0544203463797126f6eb36c8e95\",\n" +
                "                                        \"3021e6c55d6b46edb085f5ee2b7d0efe\",\n" +
                "                                        \"0acf0948a84e414cb262a0773618b087\"\n" +
                "                                    ],\n" +
                "                                    \"docName\": \"语言和时区偏好设置/Language and timezone perference\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            \"textId\": \"416092178710536\",\n" +
                "                            \"text\": \"语言和时区偏好设置/Language and timezone perference//中文版/一、背景 \\n为了支持公司的出海计划，在24年Q2，TT支持了「自定义语言和时区偏好设置」。具体包括：\\n- 可以自定义系统语言。截止24Q2，支持的语言：\\n\\t\\t- 简体中文\\n\\t\\t- 繁体中文\\n\\t\\t- 英语\\n- 可以自定义系统时区。截止24Q2，支持的时区：\\n\\t\\t- 亚洲/上海（UTC+08:00）\\n\\t\\t- 亚洲/利雅得（UTC+03:00）\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"qaType\": null,\n" +
                "                            \"recallSource\": \"SIMILARITY\",\n" +
                "                            \"recallPattern\": \"HYBRID_RECALL\",\n" +
                "                            \"rerankModel\": \"AVERAGE_RANK\",\n" +
                "                            \"score\": 0.40771812809882235,\n" +
                "                            \"businessName\": \"416092088770567\",\n" +
                "                            \"position\": 1,\n" +
                "                            \"richData\": {\n" +
                "                                \"richMaps\": {\n" +
                "                                    \"30f4a0ec-2f0f-4fa3-88c6-6d0a0393d37b\": {\n" +
                "                                        \"id\": \"30f4a0ec-2f0f-4fa3-88c6-6d0a0393d37b\",\n" +
                "                                        \"name\": \"image.jpeg\",\n" +
                "                                        \"contentType\": \"IMAGE\",\n" +
                "                                        \"mime\": \"HREF\",\n" +
                "                                        \"data\": \"https://km.sankuai.com/api/file/cdn/1497984607/18892023616?contentType=1&isNewContent=false\",\n" +
                "                                        \"publicAccess\": false\n" +
                "                                    },\n" +
                "                                    \"781b73e8-8bb9-4217-96e1-494cf2cef34d\": {\n" +
                "                                        \"id\": \"781b73e8-8bb9-4217-96e1-494cf2cef34d\",\n" +
                "                                        \"name\": \"image.jpeg\",\n" +
                "                                        \"contentType\": \"IMAGE\",\n" +
                "                                        \"mime\": \"HREF\",\n" +
                "                                        \"data\": \"https://km.sankuai.com/api/file/cdn/1497984607/19724626536?contentType=1&isNewContent=false\",\n" +
                "                                        \"publicAccess\": false\n" +
                "                                    },\n" +
                "                                    \"65b443ff-9be9-43eb-8317-4e5ddf7e9cac\": {\n" +
                "                                        \"id\": \"65b443ff-9be9-43eb-8317-4e5ddf7e9cac\",\n" +
                "                                        \"name\": \"image.jpeg\",\n" +
                "                                        \"contentType\": \"IMAGE\",\n" +
                "                                        \"mime\": \"HREF\",\n" +
                "                                        \"data\": \"https://km.sankuai.com/api/file/cdn/1497984607/18892499540?contentType=1&isNewContent=false\",\n" +
                "                                        \"publicAccess\": false\n" +
                "                                    },\n" +
                "                                    \"8528607e-33ea-4087-ad71-ed94517438aa\": {\n" +
                "                                        \"id\": \"8528607e-33ea-4087-ad71-ed94517438aa\",\n" +
                "                                        \"name\": \"image.jpeg\",\n" +
                "                                        \"contentType\": \"IMAGE\",\n" +
                "                                        \"mime\": \"HREF\",\n" +
                "                                        \"data\": \"https://km.sankuai.com/api/file/cdn/1497984607/18892907139?contentType=1&isNewContent=false\",\n" +
                "                                        \"publicAccess\": false\n" +
                "                                    }\n" +
                "                                },\n" +
                "                                \"source\": {\n" +
                "                                    \"type\": \"KM\",\n" +
                "                                    \"docId\": 1497984607,\n" +
                "                                    \"nodeIds\": [\n" +
                "                                        \"a5896e6b950845f7afa1f585e0e206a6\",\n" +
                "                                        \"f77e594e9c6f47a3bc2797eb5f8c6b2f\",\n" +
                "                                        \"79f9029615254ffa8cfee2c628870ffa\",\n" +
                "                                        \"f2508f69b5a748198bbc58433cfbb678\",\n" +
                "                                        \"b509f6ee6b3e4097a445cf9a1a688bdb\"\n" +
                "                                    ],\n" +
                "                                    \"docName\": \"TT知识库操作手册\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            \"textId\": \"416092182921219\",\n" +
                "                            \"text\": \"TT知识库操作手册//一、后台配置/1.2 摩西后台配置\\n由于知识库依赖摩西侧的相关配置，因此为了保证大家在工单处理过程中能高效使用，需要提前在摩西侧开启一下配置：\\n- \u200B开启对应机器人的「输入联想」，开启方式如下图\\n{{8528607e-33ea-4087-ad71-ed94517438aa}}\\n- 开启对应机器人的用户反馈，开启方式如下图\\n{{65b443ff-9be9-43eb-8317-4e5ddf7e9cac}}{{30f4a0ec-2f0f-4fa3-88c6-6d0a0393d37b}}\\n- 需要添加至少一个多轮对话task，可以不上线\\n{{781b73e8-8bb9-4217-96e1-494cf2cef34d}}\\n\"\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"faqRecallItems\": null\n" +
                "                },\n" +
                "                \"messageId\": \"1925174189766357085\",\n" +
                "                \"conversationId\": \"20250521205441_1912070190473265161_8366624\",\n" +
                "                \"isTransferredToManual\": false,\n" +
                "                \"feedbackOfLike\": 0,\n" +
                "                \"feedbackOfDislike\": 0,\n" +
                "                \"welcomeMessage\": false,\n" +
                "                \"isLabeled\": false,\n" +
                "                \"audioEnabled\": false,\n" +
                "                \"additionalMessage\": null,\n" +
                "                \"extendInfo\": {\n" +
                "                    \"clickTextList\": null\n" +
                "                },\n" +
                "                \"reasoningContent\": \"\",\n" +
                "                \"eventMessages\": null\n" +
                "            },\n" +
                "            {\n" +
                "                \"role\": \"user\",\n" +
                "                \"time\": \"2025-05-21 20:57:49\",\n" +
                "                \"message\": \"阿萨德撒\",\n" +
                "                \"like\": 0,\n" +
                "                \"debugInfo\": null,\n" +
                "                \"recallCount\": null,\n" +
                "                \"recallInfo\": null,\n" +
                "                \"messageId\": \"1925174189753516124\",\n" +
                "                \"conversationId\": \"20250521205441_1912070190473265161_8366624\",\n" +
                "                \"isTransferredToManual\": false,\n" +
                "                \"feedbackOfLike\": 0,\n" +
                "                \"feedbackOfDislike\": 0,\n" +
                "                \"welcomeMessage\": false,\n" +
                "                \"isLabeled\": false,\n" +
                "                \"audioEnabled\": false,\n" +
                "                \"additionalMessage\": null,\n" +
                "                \"extendInfo\": null,\n" +
                "                \"reasoningContent\": null,\n" +
                "                \"eventMessages\": null\n" +
                "            },\n" +
                "            {\n" +
                "                \"role\": \"assistant\",\n" +
                "                \"time\": \"2025-05-21 20:54:51\",\n" +
                "                \"message\": \"很抱歉，文档中没有你想要的答案。\",\n" +
                "                \"like\": 0,\n" +
                "                \"debugInfo\": {},\n" +
                "                \"recallCount\": null,\n" +
                "                \"recallInfo\": {\n" +
                "                    \"docRecallItems\": [\n" +
                "                        {\n" +
                "                            \"qaType\": null,\n" +
                "                            \"recallSource\": \"SIMILARITY\",\n" +
                "                            \"recallPattern\": \"HYBRID_RECALL\",\n" +
                "                            \"rerankModel\": \"AVERAGE_RANK\",\n" +
                "                            \"score\": 0.48835685332042794,\n" +
                "                            \"businessName\": \"416092089188354\",\n" +
                "                            \"position\": 0,\n" +
                "                            \"richData\": {\n" +
                "                                \"richMaps\": {},\n" +
                "                                \"source\": {\n" +
                "                                    \"type\": \"KM\",\n" +
                "                                    \"docId\": 995438422,\n" +
                "                                    \"nodeIds\": [\n" +
                "                                        \"e8547ac1cba5497880dff19ad9bf7ab2\",\n" +
                "                                        \"c9d11d68d9a4477bb5624d338afc7d4d\"\n" +
                "                                    ],\n" +
                "                                    \"docName\": \"TT空间使用介绍\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            \"textId\": \"416092181463041\",\n" +
                "                            \"text\": \"\\n空间：是为了将业务目录（自有空间）与公司通用目录（公共空间）隔离而存在的。通过空间，各个业务部门可根据自身需求，自行创建、修改一二三级目录，并拥有一个专属的空间访问链接，这个链接中只能看到自己空间的目录，他人点击这个链接也只能向这个空间的目录发起TT。 通过空间，您可以实现只让发起方看到指定目录的需求，发起方看到的目录数量更少，选择目录将更加快捷、高效。\\n\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"qaType\": null,\n" +
                "                            \"recallSource\": \"SIMILARITY\",\n" +
                "                            \"recallPattern\": \"HYBRID_RECALL\",\n" +
                "                            \"rerankModel\": \"AVERAGE_RANK\",\n" +
                "                            \"score\": 0.486651768706482,\n" +
                "                            \"businessName\": \"416092089188359\",\n" +
                "                            \"position\": 0,\n" +
                "                            \"richData\": {\n" +
                "                                \"richMaps\": {},\n" +
                "                                \"source\": {\n" +
                "                                    \"type\": \"KM\",\n" +
                "                                    \"docId\": 980521401,\n" +
                "                                    \"nodeIds\": [\n" +
                "                                        \"a08882edca9445b89566157bd818ae4a\"\n" +
                "                                    ],\n" +
                "                                    \"docName\": \"RG组绑定服务目录\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            \"textId\": \"416092181045249\",\n" +
                "                            \"text\": \"\\n空间：是为了将业务目录（自有空间）与公司通用目录（公共空间）隔离而存在的。通过空间，各个业务部门可以根据自身需求，自行创建、修改一二三级目录，并拥有一个专属的空间访问链接，这个链接中只能看到自己空间中的目录，他人点击这个链接也只能向这个空间的目录发起TT。\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"qaType\": null,\n" +
                "                            \"recallSource\": \"SIMILARITY\",\n" +
                "                            \"recallPattern\": \"HYBRID_RECALL\",\n" +
                "                            \"rerankModel\": \"AVERAGE_RANK\",\n" +
                "                            \"score\": 0.47968505355493324,\n" +
                "                            \"businessName\": \"416092089188360\",\n" +
                "                            \"position\": 0,\n" +
                "                            \"richData\": {\n" +
                "                                \"richMaps\": {},\n" +
                "                                \"source\": {\n" +
                "                                    \"type\": \"KM\",\n" +
                "                                    \"docId\": 2258634407,\n" +
                "                                    \"nodeIds\": [\n" +
                "                                        \"437bc7cc52564321846581edcb815365\",\n" +
                "                                        \"41427ecbd5cc4525a3e2c3e3358c05b5\"\n" +
                "                                    ],\n" +
                "                                    \"docName\": \"语言和时区偏好设置/Language and timezone perference\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            \"textId\": \"416092178710529\",\n" +
                "                            \"text\": \"\\n\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"qaType\": null,\n" +
                "                            \"recallSource\": \"SIMILARITY\",\n" +
                "                            \"recallPattern\": \"HYBRID_RECALL\",\n" +
                "                            \"rerankModel\": \"AVERAGE_RANK\",\n" +
                "                            \"score\": 0.47734125270685795,\n" +
                "                            \"businessName\": \"416092088770567\",\n" +
                "                            \"position\": 1,\n" +
                "                            \"richData\": {\n" +
                "                                \"richMaps\": {\n" +
                "                                    \"30f4a0ec-2f0f-4fa3-88c6-6d0a0393d37b\": {\n" +
                "                                        \"id\": \"30f4a0ec-2f0f-4fa3-88c6-6d0a0393d37b\",\n" +
                "                                        \"name\": \"image.jpeg\",\n" +
                "                                        \"contentType\": \"IMAGE\",\n" +
                "                                        \"mime\": \"HREF\",\n" +
                "                                        \"data\": \"https://km.sankuai.com/api/file/cdn/1497984607/18892023616?contentType=1&isNewContent=false\",\n" +
                "                                        \"publicAccess\": false\n" +
                "                                    },\n" +
                "                                    \"781b73e8-8bb9-4217-96e1-494cf2cef34d\": {\n" +
                "                                        \"id\": \"781b73e8-8bb9-4217-96e1-494cf2cef34d\",\n" +
                "                                        \"name\": \"image.jpeg\",\n" +
                "                                        \"contentType\": \"IMAGE\",\n" +
                "                                        \"mime\": \"HREF\",\n" +
                "                                        \"data\": \"https://km.sankuai.com/api/file/cdn/1497984607/19724626536?contentType=1&isNewContent=false\",\n" +
                "                                        \"publicAccess\": false\n" +
                "                                    },\n" +
                "                                    \"65b443ff-9be9-43eb-8317-4e5ddf7e9cac\": {\n" +
                "                                        \"id\": \"65b443ff-9be9-43eb-8317-4e5ddf7e9cac\",\n" +
                "                                        \"name\": \"image.jpeg\",\n" +
                "                                        \"contentType\": \"IMAGE\",\n" +
                "                                        \"mime\": \"HREF\",\n" +
                "                                        \"data\": \"https://km.sankuai.com/api/file/cdn/1497984607/18892499540?contentType=1&isNewContent=false\",\n" +
                "                                        \"publicAccess\": false\n" +
                "                                    },\n" +
                "                                    \"8528607e-33ea-4087-ad71-ed94517438aa\": {\n" +
                "                                        \"id\": \"8528607e-33ea-4087-ad71-ed94517438aa\",\n" +
                "                                        \"name\": \"image.jpeg\",\n" +
                "                                        \"contentType\": \"IMAGE\",\n" +
                "                                        \"mime\": \"HREF\",\n" +
                "                                        \"data\": \"https://km.sankuai.com/api/file/cdn/1497984607/18892907139?contentType=1&isNewContent=false\",\n" +
                "                                        \"publicAccess\": false\n" +
                "                                    }\n" +
                "                                },\n" +
                "                                \"source\": {\n" +
                "                                    \"type\": \"KM\",\n" +
                "                                    \"docId\": 1497984607,\n" +
                "                                    \"nodeIds\": [\n" +
                "                                        \"a5896e6b950845f7afa1f585e0e206a6\",\n" +
                "                                        \"f77e594e9c6f47a3bc2797eb5f8c6b2f\",\n" +
                "                                        \"79f9029615254ffa8cfee2c628870ffa\",\n" +
                "                                        \"f2508f69b5a748198bbc58433cfbb678\",\n" +
                "                                        \"b509f6ee6b3e4097a445cf9a1a688bdb\"\n" +
                "                                    ],\n" +
                "                                    \"docName\": \"TT知识库操作手册\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            \"textId\": \"416092182921219\",\n" +
                "                            \"text\": \"TT知识库操作手册//一、后台配置/1.2 摩西后台配置\\n由于知识库依赖摩西侧的相关配置，因此为了保证大家在工单处理过程中能高效使用，需要提前在摩西侧开启一下配置：\\n- \u200B开启对应机器人的「输入联想」，开启方式如下图\\n{{8528607e-33ea-4087-ad71-ed94517438aa}}\\n- 开启对应机器人的用户反馈，开启方式如下图\\n{{65b443ff-9be9-43eb-8317-4e5ddf7e9cac}}{{30f4a0ec-2f0f-4fa3-88c6-6d0a0393d37b}}\\n- 需要添加至少一个多轮对话task，可以不上线\\n{{781b73e8-8bb9-4217-96e1-494cf2cef34d}}\\n\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"qaType\": null,\n" +
                "                            \"recallSource\": \"SIMILARITY\",\n" +
                "                            \"recallPattern\": \"HYBRID_RECALL\",\n" +
                "                            \"rerankModel\": \"AVERAGE_RANK\",\n" +
                "                            \"score\": 0.4772361419344356,\n" +
                "                            \"businessName\": \"416092089188360\",\n" +
                "                            \"position\": 8,\n" +
                "                            \"richData\": {\n" +
                "                                \"richMaps\": {},\n" +
                "                                \"source\": {\n" +
                "                                    \"type\": \"KM\",\n" +
                "                                    \"docId\": 2258634407,\n" +
                "                                    \"nodeIds\": [\n" +
                "                                        \"dc80bb185d094e2c91f1abad3545af04\",\n" +
                "                                        \"1446a0544203463797126f6eb36c8e95\",\n" +
                "                                        \"6f797fefd35046bdb90429e2530fe20d\"\n" +
                "                                    ],\n" +
                "                                    \"docName\": \"语言和时区偏好设置/Language and timezone perference\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            \"textId\": \"416092178710538\",\n" +
                "                            \"text\": \"语言和时区偏好设置/Language and timezone perference//中文版/二、自定义语言和时区偏好设置\\n暂不隐藏入口，如需基于工单沟通可通过拉群解决。4、紧急工单催办机制暂时无法触发外呼（木星不支持），提醒业务侧关注大象催办消息。\"\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"faqRecallItems\": null\n" +
                "                },\n" +
                "                \"messageId\": \"1925173435185635398\",\n" +
                "                \"conversationId\": \"20250521205441_1912070190473265161_8366624\",\n" +
                "                \"isTransferredToManual\": false,\n" +
                "                \"feedbackOfLike\": 0,\n" +
                "                \"feedbackOfDislike\": 0,\n" +
                "                \"welcomeMessage\": false,\n" +
                "                \"isLabeled\": false,\n" +
                "                \"audioEnabled\": false,\n" +
                "                \"additionalMessage\": null,\n" +
                "                \"extendInfo\": {\n" +
                "                    \"clickTextList\": null\n" +
                "                },\n" +
                "                \"reasoningContent\": \"\",\n" +
                "                \"eventMessages\": null\n" +
                "            },\n" +
                "            {\n" +
                "                \"role\": \"user\",\n" +
                "                \"time\": \"2025-05-21 20:54:49\",\n" +
                "                \"message\": \"大厦\",\n" +
                "                \"like\": 0,\n" +
                "                \"debugInfo\": {},\n" +
                "                \"recallCount\": null,\n" +
                "                \"recallInfo\": null,\n" +
                "                \"messageId\": \"1925173435177209879\",\n" +
                "                \"conversationId\": \"20250521205441_1912070190473265161_8366624\",\n" +
                "                \"isTransferredToManual\": false,\n" +
                "                \"feedbackOfLike\": 0,\n" +
                "                \"feedbackOfDislike\": 0,\n" +
                "                \"welcomeMessage\": false,\n" +
                "                \"isLabeled\": false,\n" +
                "                \"audioEnabled\": false,\n" +
                "                \"additionalMessage\": null,\n" +
                "                \"extendInfo\": null,\n" +
                "                \"reasoningContent\": null,\n" +
                "                \"eventMessages\": null\n" +
                "            },\n" +
                "            {\n" +
                "                \"role\": \"assistant\",\n" +
                "                \"time\": \"2025-05-21 20:54:48\",\n" +
                "                \"message\": \"很抱歉，文档中没有你想要的答案。\",\n" +
                "                \"like\": 0,\n" +
                "                \"debugInfo\": {},\n" +
                "                \"recallCount\": null,\n" +
                "                \"recallInfo\": {\n" +
                "                    \"docRecallItems\": [\n" +
                "                        {\n" +
                "                            \"qaType\": null,\n" +
                "                            \"recallSource\": \"SIMILARITY\",\n" +
                "                            \"recallPattern\": \"HYBRID_RECALL\",\n" +
                "                            \"rerankModel\": \"AVERAGE_RANK\",\n" +
                "                            \"score\": 0.48579795592709196,\n" +
                "                            \"businessName\": \"416092088770566\",\n" +
                "                            \"position\": 9,\n" +
                "                            \"richData\": {\n" +
                "                                \"richMaps\": {},\n" +
                "                                \"source\": {\n" +
                "                                    \"type\": \"KM\",\n" +
                "                                    \"docId\": 995395988,\n" +
                "                                    \"nodeIds\": [\n" +
                "                                        \"975f5f51fe7b40e7a383bbf2a3abf799\"\n" +
                "                                    ],\n" +
                "                                    \"docName\": \"发起TT\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            \"textId\": \"416092178726921\",\n" +
                "                            \"text\": \"发起TT//\\n\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"qaType\": null,\n" +
                "                            \"recallSource\": \"SIMILARITY\",\n" +
                "                            \"recallPattern\": \"HYBRID_RECALL\",\n" +
                "                            \"rerankModel\": \"AVERAGE_RANK\",\n" +
                "                            \"score\": 0.4852416866414946,\n" +
                "                            \"businessName\": \"416092089188360\",\n" +
                "                            \"position\": 5,\n" +
                "                            \"richData\": {\n" +
                "                                \"richMaps\": {},\n" +
                "                                \"source\": {\n" +
                "                                    \"type\": \"KM\",\n" +
                "                                    \"docId\": 2258634407,\n" +
                "                                    \"nodeIds\": [\n" +
                "                                        \"f70642fc23a34e46810237535171856a\",\n" +
                "                                        \"26b8e7ad6fcd420c8b79eb05662377e4\",\n" +
                "                                        \"83809f6b22974df6bd607c2ceb11f08a\",\n" +
                "                                        \"3de5efa789394991b0a8d6d8e1a2456d\",\n" +
                "                                        \"26400d25a5834e1e832a89b320fd2b79\"\n" +
                "                                    ],\n" +
                "                                    \"docName\": \"语言和时区偏好设置/Language and timezone perference\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            \"textId\": \"416092178710535\",\n" +
                "                            \"text\": \"语言和时区偏好设置/Language and timezone perference//English Version/2. Customized Language and Time Zone Preference \\nand if necessary, it will be operated by domestic PM and R&D teams.\\n3. The embedded communication tool \\\"Daxiang\\\" in TT does not support English. The entry point is not hidden for now, and if communication based on TT is needed, it can be resolved through group communication.\\n4. The mechanism for urgent TT reminders cannot trigger outbound phone calls temporarily (not supported by Jupiter). It is recommended that the business side pay attention to Daxiang warning messages.\\n\\n\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"qaType\": null,\n" +
                "                            \"recallSource\": \"SIMILARITY\",\n" +
                "                            \"recallPattern\": \"HYBRID_RECALL\",\n" +
                "                            \"rerankModel\": \"AVERAGE_RANK\",\n" +
                "                            \"score\": 0.4604424813131127,\n" +
                "                            \"businessName\": \"416092089188360\",\n" +
                "                            \"position\": 6,\n" +
                "                            \"richData\": {\n" +
                "                                \"richMaps\": {},\n" +
                "                                \"source\": {\n" +
                "                                    \"type\": \"KM\",\n" +
                "                                    \"docId\": 2258634407,\n" +
                "                                    \"nodeIds\": [\n" +
                "                                        \"3ac5697c885f4fa8adac873f58bbf994\",\n" +
                "                                        \"1446a0544203463797126f6eb36c8e95\",\n" +
                "                                        \"3021e6c55d6b46edb085f5ee2b7d0efe\",\n" +
                "                                        \"0acf0948a84e414cb262a0773618b087\"\n" +
                "                                    ],\n" +
                "                                    \"docName\": \"语言和时区偏好设置/Language and timezone perference\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            \"textId\": \"416092178710536\",\n" +
                "                            \"text\": \"语言和时区偏好设置/Language and timezone perference//中文版/一、背景 \\n为了支持公司的出海计划，在24年Q2，TT支持了「自定义语言和时区偏好设置」。具体包括：\\n- 可以自定义系统语言。截止24Q2，支持的语言：\\n\\t\\t- 简体中文\\n\\t\\t- 繁体中文\\n\\t\\t- 英语\\n- 可以自定义系统时区。截止24Q2，支持的时区：\\n\\t\\t- 亚洲/上海（UTC+08:00）\\n\\t\\t- 亚洲/利雅得（UTC+03:00）\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"qaType\": null,\n" +
                "                            \"recallSource\": \"SIMILARITY\",\n" +
                "                            \"recallPattern\": \"HYBRID_RECALL\",\n" +
                "                            \"rerankModel\": \"AVERAGE_RANK\",\n" +
                "                            \"score\": 0.4527155186348845,\n" +
                "                            \"businessName\": \"416092089188358\",\n" +
                "                            \"position\": 3,\n" +
                "                            \"richData\": {\n" +
                "                                \"richMaps\": {\n" +
                "                                    \"046dd0c6-c656-49db-a70d-77f36c9217dd\": {\n" +
                "                                        \"id\": \"046dd0c6-c656-49db-a70d-77f36c9217dd\",\n" +
                "                                        \"name\": \"https://km.sankuai.com/page/995438422\",\n" +
                "                                        \"contentType\": \"LINK\",\n" +
                "                                        \"mime\": \"HREF\",\n" +
                "                                        \"data\": \"https://km.sankuai.com/page/995438422\",\n" +
                "                                        \"publicAccess\": false\n" +
                "                                    },\n" +
                "                                    \"cba86307-123d-4d1d-b195-d5f46f3914bb\": {\n" +
                "                                        \"id\": \"cba86307-123d-4d1d-b195-d5f46f3914bb\",\n" +
                "                                        \"name\": \"https://km.sankuai.com/page/995438432\",\n" +
                "                                        \"contentType\": \"LINK\",\n" +
                "                                        \"mime\": \"HREF\",\n" +
                "                                        \"data\": \"https://km.sankuai.com/page/995438432\",\n" +
                "                                        \"publicAccess\": false\n" +
                "                                    }\n" +
                "                                },\n" +
                "                                \"source\": {\n" +
                "                                    \"type\": \"KM\",\n" +
                "                                    \"docId\": 1045757620,\n" +
                "                                    \"nodeIds\": [\n" +
                "                                        \"8c5670e771d646199c4134bce74298f6\",\n" +
                "                                        \"2183c8847d2b40fdb2797b4fad581a6a\"\n" +
                "                                    ],\n" +
                "                                    \"docName\": \"常用功能介绍\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            \"textId\": \"416092178948100\",\n" +
                "                            \"text\": \"|  | SLA设置 | - 「服务黄页」➡\uFE0F「服务组(RG)」➡\uFE0F  选择要设置SLA的服务组 ➡\uFE0F 「SLA等级」 | {{cba86307-123d-4d1d-b195-d5f46f3914bb}} |\\n|  | 自有空间管理 | - 「服务黄页」➡\uFE0F「空间管理」➡\uFE0F  选择要操作的空间➡\uFE0F「空间管理员管理/RG组管理(添加/移除/设置RG组)/目录管理」 | {{046dd0c6-c656-49db-a70d-77f36c9217dd}} |\\n\\\"\\\"\\\"\\n\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"qaType\": null,\n" +
                "                            \"recallSource\": \"SIMILARITY\",\n" +
                "                            \"recallPattern\": \"HYBRID_RECALL\",\n" +
                "                            \"rerankModel\": \"AVERAGE_RANK\",\n" +
                "                            \"score\": 0.4432120777041965,\n" +
                "                            \"businessName\": \"416092088770567\",\n" +
                "                            \"position\": 1,\n" +
                "                            \"richData\": {\n" +
                "                                \"richMaps\": {\n" +
                "                                    \"30f4a0ec-2f0f-4fa3-88c6-6d0a0393d37b\": {\n" +
                "                                        \"id\": \"30f4a0ec-2f0f-4fa3-88c6-6d0a0393d37b\",\n" +
                "                                        \"name\": \"image.jpeg\",\n" +
                "                                        \"contentType\": \"IMAGE\",\n" +
                "                                        \"mime\": \"HREF\",\n" +
                "                                        \"data\": \"https://km.sankuai.com/api/file/cdn/1497984607/18892023616?contentType=1&isNewContent=false\",\n" +
                "                                        \"publicAccess\": false\n" +
                "                                    },\n" +
                "                                    \"781b73e8-8bb9-4217-96e1-494cf2cef34d\": {\n" +
                "                                        \"id\": \"781b73e8-8bb9-4217-96e1-494cf2cef34d\",\n" +
                "                                        \"name\": \"image.jpeg\",\n" +
                "                                        \"contentType\": \"IMAGE\",\n" +
                "                                        \"mime\": \"HREF\",\n" +
                "                                        \"data\": \"https://km.sankuai.com/api/file/cdn/1497984607/19724626536?contentType=1&isNewContent=false\",\n" +
                "                                        \"publicAccess\": false\n" +
                "                                    },\n" +
                "                                    \"65b443ff-9be9-43eb-8317-4e5ddf7e9cac\": {\n" +
                "                                        \"id\": \"65b443ff-9be9-43eb-8317-4e5ddf7e9cac\",\n" +
                "                                        \"name\": \"image.jpeg\",\n" +
                "                                        \"contentType\": \"IMAGE\",\n" +
                "                                        \"mime\": \"HREF\",\n" +
                "                                        \"data\": \"https://km.sankuai.com/api/file/cdn/1497984607/18892499540?contentType=1&isNewContent=false\",\n" +
                "                                        \"publicAccess\": false\n" +
                "                                    },\n" +
                "                                    \"8528607e-33ea-4087-ad71-ed94517438aa\": {\n" +
                "                                        \"id\": \"8528607e-33ea-4087-ad71-ed94517438aa\",\n" +
                "                                        \"name\": \"image.jpeg\",\n" +
                "                                        \"contentType\": \"IMAGE\",\n" +
                "                                        \"mime\": \"HREF\",\n" +
                "                                        \"data\": \"https://km.sankuai.com/api/file/cdn/1497984607/18892907139?contentType=1&isNewContent=false\",\n" +
                "                                        \"publicAccess\": false\n" +
                "                                    }\n" +
                "                                },\n" +
                "                                \"source\": {\n" +
                "                                    \"type\": \"KM\",\n" +
                "                                    \"docId\": 1497984607,\n" +
                "                                    \"nodeIds\": [\n" +
                "                                        \"a5896e6b950845f7afa1f585e0e206a6\",\n" +
                "                                        \"f77e594e9c6f47a3bc2797eb5f8c6b2f\",\n" +
                "                                        \"79f9029615254ffa8cfee2c628870ffa\",\n" +
                "                                        \"f2508f69b5a748198bbc58433cfbb678\",\n" +
                "                                        \"b509f6ee6b3e4097a445cf9a1a688bdb\"\n" +
                "                                    ],\n" +
                "                                    \"docName\": \"TT知识库操作手册\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            \"textId\": \"416092182921219\",\n" +
                "                            \"text\": \"TT知识库操作手册//一、后台配置/1.2 摩西后台配置\\n由于知识库依赖摩西侧的相关配置，因此为了保证大家在工单处理过程中能高效使用，需要提前在摩西侧开启一下配置：\\n- \u200B开启对应机器人的「输入联想」，开启方式如下图\\n{{8528607e-33ea-4087-ad71-ed94517438aa}}\\n- 开启对应机器人的用户反馈，开启方式如下图\\n{{65b443ff-9be9-43eb-8317-4e5ddf7e9cac}}{{30f4a0ec-2f0f-4fa3-88c6-6d0a0393d37b}}\\n- 需要添加至少一个多轮对话task，可以不上线\\n{{781b73e8-8bb9-4217-96e1-494cf2cef34d}}\\n\"\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"faqRecallItems\": null\n" +
                "                },\n" +
                "                \"messageId\": \"1925173425970937899\",\n" +
                "                \"conversationId\": \"20250521205441_1912070190473265161_8366624\",\n" +
                "                \"isTransferredToManual\": false,\n" +
                "                \"feedbackOfLike\": 0,\n" +
                "                \"feedbackOfDislike\": 0,\n" +
                "                \"welcomeMessage\": false,\n" +
                "                \"isLabeled\": false,\n" +
                "                \"audioEnabled\": false,\n" +
                "                \"additionalMessage\": null,\n" +
                "                \"extendInfo\": {\n" +
                "                    \"clickTextList\": null\n" +
                "                },\n" +
                "                \"reasoningContent\": \"\",\n" +
                "                \"eventMessages\": null\n" +
                "            },\n" +
                "            {\n" +
                "                \"role\": \"user\",\n" +
                "                \"time\": \"2025-05-21 20:54:47\",\n" +
                "                \"message\": \"dsadsad撒打算\",\n" +
                "                \"like\": 0,\n" +
                "                \"debugInfo\": null,\n" +
                "                \"recallCount\": null,\n" +
                "                \"recallInfo\": null,\n" +
                "                \"messageId\": \"1925173425962688526\",\n" +
                "                \"conversationId\": \"20250521205441_1912070190473265161_8366624\",\n" +
                "                \"isTransferredToManual\": false,\n" +
                "                \"feedbackOfLike\": 0,\n" +
                "                \"feedbackOfDislike\": 0,\n" +
                "                \"welcomeMessage\": false,\n" +
                "                \"isLabeled\": false,\n" +
                "                \"audioEnabled\": false,\n" +
                "                \"additionalMessage\": null,\n" +
                "                \"extendInfo\": null,\n" +
                "                \"reasoningContent\": null,\n" +
                "                \"eventMessages\": null\n" +
                "            },\n" +
                "            {\n" +
                "                \"role\": \"assistant\",\n" +
                "                \"time\": \"2025-05-21 20:54:46\",\n" +
                "                \"message\": \"很抱歉，文档中没有你想要的答案。\",\n" +
                "                \"like\": 0,\n" +
                "                \"debugInfo\": {},\n" +
                "                \"recallCount\": null,\n" +
                "                \"recallInfo\": {\n" +
                "                    \"docRecallItems\": [\n" +
                "                        {\n" +
                "                            \"qaType\": null,\n" +
                "                            \"recallSource\": \"SIMILARITY\",\n" +
                "                            \"recallPattern\": \"HYBRID_RECALL\",\n" +
                "                            \"rerankModel\": \"AVERAGE_RANK\",\n" +
                "                            \"score\": 0.4482685865132008,\n" +
                "                            \"businessName\": \"416092089188357\",\n" +
                "                            \"position\": 4,\n" +
                "                            \"richData\": {\n" +
                "                                \"richMaps\": {},\n" +
                "                                \"source\": {\n" +
                "                                    \"type\": \"KM\",\n" +
                "                                    \"docId\": 995297502,\n" +
                "                                    \"nodeIds\": [\n" +
                "                                        \"10551c7b11d643148df6fc357e802315\",\n" +
                "                                        \"8a0e0ef1d1c2434cab67ed83e1f3fd69\"\n" +
                "                                    ],\n" +
                "                                    \"docName\": \"处理TT\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            \"textId\": \"416092180824068\",\n" +
                "                            \"text\": \"处理TT// 二、处理TT步骤介绍\\n将问题流转到正确的处理团队。小技巧：可以通过系统智能推荐目录进行流转\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"qaType\": null,\n" +
                "                            \"recallSource\": \"SIMILARITY\",\n" +
                "                            \"recallPattern\": \"HYBRID_RECALL\",\n" +
                "                            \"rerankModel\": \"AVERAGE_RANK\",\n" +
                "                            \"score\": 0.4436796504837533,\n" +
                "                            \"businessName\": \"416092089188360\",\n" +
                "                            \"position\": 0,\n" +
                "                            \"richData\": {\n" +
                "                                \"richMaps\": {},\n" +
                "                                \"source\": {\n" +
                "                                    \"type\": \"KM\",\n" +
                "                                    \"docId\": 2258634407,\n" +
                "                                    \"nodeIds\": [\n" +
                "                                        \"437bc7cc52564321846581edcb815365\",\n" +
                "                                        \"41427ecbd5cc4525a3e2c3e3358c05b5\"\n" +
                "                                    ],\n" +
                "                                    \"docName\": \"语言和时区偏好设置/Language and timezone perference\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            \"textId\": \"416092178710529\",\n" +
                "                            \"text\": \"\\n\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"qaType\": null,\n" +
                "                            \"recallSource\": \"SIMILARITY\",\n" +
                "                            \"recallPattern\": \"HYBRID_RECALL\",\n" +
                "                            \"rerankModel\": \"AVERAGE_RANK\",\n" +
                "                            \"score\": 0.439161755469919,\n" +
                "                            \"businessName\": \"421662598201345\",\n" +
                "                            \"position\": 6,\n" +
                "                            \"richData\": {\n" +
                "                                \"richMaps\": {},\n" +
                "                                \"source\": {\n" +
                "                                    \"type\": \"KM\",\n" +
                "                                    \"docId\": 999869721,\n" +
                "                                    \"nodeIds\": [\n" +
                "                                        \"2d11810416d3471680d73ce48256911e\",\n" +
                "                                        \"778af10741fa44dc92e01929a0bcf89b\",\n" +
                "                                        \"c1ec3b6cfb204824938d718fdf1dae1d\",\n" +
                "                                        \"c30da5f118d54b20a609d9046fedb2fb\",\n" +
                "                                        \"bbcee4eeaebe46f39c3792efdbd9a928\",\n" +
                "                                        \"bd0d4fe2461b40d9b8a5a2bd299611aa\",\n" +
                "                                        \"ae95b233238246518edddee873bf59a7\",\n" +
                "                                        \"190df744db3941edad9980315cd6630b\",\n" +
                "                                        \"416efaa3049c4ccb8a621a461bd96a85\",\n" +
                "                                        \"f9de61a57330417cb553a0b40712bd9e\",\n" +
                "                                        \"85e2459b0aff4ddcbdf7af439dbb82ad\",\n" +
                "                                        \"04159598d8c74898b489a91aa5d842af\",\n" +
                "                                        \"91668c76d3eb4182b86fe4fc94a6fb36\",\n" +
                "                                        \"5ec856e365054d5faaa4eecb75bcfe69\",\n" +
                "                                        \"256ec46b33fc41a09bed85b3d9c63335\",\n" +
                "                                        \"27d30b0865124b15897d96ba69e15262\",\n" +
                "                                        \"8f934f0fc34a4b9a96fa0b5fcca07920\",\n" +
                "                                        \"a04c435a873f4420aa56c2e98f9838ab\",\n" +
                "                                        \"255e8d88b6a7415187b1116e9ebe2ae4\"\n" +
                "                                    ],\n" +
                "                                    \"docName\": \"TT-FAQ\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            \"textId\": \"421662619410438\",\n" +
                "                            \"text\": \"TT-FAQ//问题处理方相关\\n来指定处理人\\n\\nQ：**配置了触发器，为什么没生效**\\nA：1.在RG组-触发器中，检查一下触发期是否启用；2.检查一下目录绑定的模版显示了处理人（如显示了处理人，不会执行触发器）\\n\\nQ：**怎么设置节假日值班？**\\nA：在RG组-SLA等级-工作时间中，设置节假日/调休日值班\\n\\nQ：**怎么新建三级目录？**\\nA：在服务黄页，找到一二级目录，rg组管理员可以直接添加三级目录\\n\\n\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"qaType\": null,\n" +
                "                            \"recallSource\": \"SIMILARITY\",\n" +
                "                            \"recallPattern\": \"HYBRID_RECALL\",\n" +
                "                            \"rerankModel\": \"AVERAGE_RANK\",\n" +
                "                            \"score\": 0.4364591121387111,\n" +
                "                            \"businessName\": \"416092089188358\",\n" +
                "                            \"position\": 1,\n" +
                "                            \"richData\": {\n" +
                "                                \"richMaps\": {},\n" +
                "                                \"source\": {\n" +
                "                                    \"type\": \"KM\",\n" +
                "                                    \"docId\": 1045757620,\n" +
                "                                    \"nodeIds\": [\n" +
                "                                        \"8c5670e771d646199c4134bce74298f6\"\n" +
                "                                    ],\n" +
                "                                    \"docName\": \"常用功能介绍\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            \"textId\": \"416092178948098\",\n" +
                "                            \"text\": \"望知悉\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"qaType\": null,\n" +
                "                            \"recallSource\": \"SIMILARITY\",\n" +
                "                            \"recallPattern\": \"HYBRID_RECALL\",\n" +
                "                            \"rerankModel\": \"AVERAGE_RANK\",\n" +
                "                            \"score\": 0.43108228509712315,\n" +
                "                            \"businessName\": \"416092088533002\",\n" +
                "                            \"position\": 6,\n" +
                "                            \"richData\": {\n" +
                "                                \"richMaps\": {},\n" +
                "                                \"source\": {\n" +
                "                                    \"type\": \"KM\",\n" +
                "                                    \"docId\": 1309835886,\n" +
                "                                    \"nodeIds\": [\n" +
                "                                        \"5c3a23a080c84145898edf0ba6ae0af2\",\n" +
                "                                        \"9f33088e11a04f38a3fe9444a68824ba\"\n" +
                "                                    ],\n" +
                "                                    \"docName\": \"新版统计分析页面帮助文档\"\n" +
                "                                }\n" +
                "                            },\n" +
                "                            \"textId\": \"416092182904842\",\n" +
                "                            \"text\": \"新版统计分析页面帮助文档//四、计算方式\\n选中RG/CTI/组织下工单处理超时的工单数量 |\\n| 按时长统计 | 12h响应率 | 创建时间为选中日期，选中RG/CTI/组织下工单响应时长小于等于12小时的工单占比 |\\n| 按时长统计 | 24h处理完成率 | 创建时间为选中日期\"\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"faqRecallItems\": null\n" +
                "                },\n" +
                "                \"messageId\": \"1925173418140241980\",\n" +
                "                \"conversationId\": \"20250521205441_1912070190473265161_8366624\",\n" +
                "                \"isTransferredToManual\": false,\n" +
                "                \"feedbackOfLike\": 0,\n" +
                "                \"feedbackOfDislike\": 0,\n" +
                "                \"welcomeMessage\": false,\n" +
                "                \"isLabeled\": false,\n" +
                "                \"audioEnabled\": false,\n" +
                "                \"additionalMessage\": null,\n" +
                "                \"extendInfo\": {\n" +
                "                    \"clickTextList\": null\n" +
                "                },\n" +
                "                \"reasoningContent\": \"\",\n" +
                "                \"eventMessages\": null\n" +
                "            },\n" +
                "            {\n" +
                "                \"role\": \"user\",\n" +
                "                \"time\": \"2025-05-21 20:54:45\",\n" +
                "                \"message\": \"asdsad\",\n" +
                "                \"like\": 0,\n" +
                "                \"debugInfo\": null,\n" +
                "                \"recallCount\": null,\n" +
                "                \"recallInfo\": null,\n" +
                "                \"messageId\": \"1925173418136051781\",\n" +
                "                \"conversationId\": \"20250521205441_1912070190473265161_8366624\",\n" +
                "                \"isTransferredToManual\": false,\n" +
                "                \"feedbackOfLike\": 0,\n" +
                "                \"feedbackOfDislike\": 0,\n" +
                "                \"welcomeMessage\": false,\n" +
                "                \"isLabeled\": false,\n" +
                "                \"audioEnabled\": false,\n" +
                "                \"additionalMessage\": null,\n" +
                "                \"extendInfo\": null,\n" +
                "                \"reasoningContent\": null,\n" +
                "                \"eventMessages\": null\n" +
                "            },\n" +
                "            {\n" +
                "                \"role\": \"assistant\",\n" +
                "                \"time\": \"2025-05-21 20:54:41\",\n" +
                "                \"message\": \"<p>我是TT值班机器人，来问我问题吧\uD83D\uDE04</p>\",\n" +
                "                \"like\": 0,\n" +
                "                \"debugInfo\": null,\n" +
                "                \"recallCount\": null,\n" +
                "                \"recallInfo\": null,\n" +
                "                \"messageId\": \"1925173403128897589\",\n" +
                "                \"conversationId\": \"20250521205441_1912070190473265161_8366624\",\n" +
                "                \"isTransferredToManual\": false,\n" +
                "                \"feedbackOfLike\": 0,\n" +
                "                \"feedbackOfDislike\": 0,\n" +
                "                \"welcomeMessage\": true,\n" +
                "                \"isLabeled\": false,\n" +
                "                \"audioEnabled\": false,\n" +
                "                \"additionalMessage\": {\n" +
                "                    \"guideText\": \"你可以问我这些问题：\",\n" +
                "                    \"questions\": [\n" +
                "                        \"如何添加值班人员\",\n" +
                "                        \"如何新建RG组\",\n" +
                "                        \"如何新建目录\",\n" +
                "                        \"如何配置模版\",\n" +
                "                        \"如何设置触发器\"\n" +
                "                    ],\n" +
                "                    \"clickTextList\": null\n" +
                "                },\n" +
                "                \"extendInfo\": {\n" +
                "                    \"clickTextList\": null\n" +
                "                },\n" +
                "                \"reasoningContent\": null,\n" +
                "                \"eventMessages\": null\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";

        ObjectMapper objectMapper = new ObjectMapper();
        FridayAppFactoryResp<ChatRecordsRespPO> po = objectMapper.readValue(json, new TypeReference<FridayAppFactoryResp<ChatRecordsRespPO>>() { });
        System.out.println(po);
    }

}
